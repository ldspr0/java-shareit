package ru.practicum.shareit.item;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.exception.ConditionsNotMetException;
import ru.practicum.shareit.exception.NotFoundException;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.item.comments.Comment;
import ru.practicum.shareit.item.comments.CommentRepository;

import ru.practicum.shareit.item.comments.dto.CommentDto;
import ru.practicum.shareit.item.comments.dto.NewCommentRequest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;

import ru.practicum.shareit.item.comments.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;

import ru.practicum.shareit.validations.ServiceValidations;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ServiceValidations serviceValidations;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public ItemDto createItem(long userId, NewItemRequest request) {
        log.info("ItemServiceImpl : createItem start with userId = {} and request = {}", userId, request);
        serviceValidations.checkIfUserExistsOrThrowError(userId);

        Item item = ItemMapper.mapToItem(request);
        item.setOwnerId(userId);
        item = itemRepository.save(item);

        return ItemMapper.mapToItemDto(item);
    }

    @Override
    public ItemDto updateItem(long userId, long id, UpdateItemRequest request) {
        log.info("ItemServiceImpl : updateItem start with userId = {} and request = {}", userId, request);
        serviceValidations.checkIfUserExistsOrThrowError(userId);

        Item updatedItem = itemRepository.findById(id)
                .map(item -> ItemMapper.updateItemFields(item, request))
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));
        if (updatedItem.getOwnerId() != userId) {
            throw new ConditionsNotMetException("Изменять данные о предмете может только владелец");
        }

        return ItemMapper.mapToItemDto(updatedItem);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ItemDto> getAllItems(long userId) {
        log.info("ItemServiceImpl : getAllItems start with userId = {}", userId);
        return itemRepository.findByOwnerId(userId).stream()
                .map(ItemMapper::mapToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ItemDto> search(String text) {
        log.info("ItemServiceImpl : search start with text = {}", text);
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        return itemRepository.findByNameContainingIgnoreCaseAndAvailableIsTrueOrDescriptionContainingIgnoreCaseAndAvailableIsTrue(text, text).stream()
                .map(ItemMapper::mapToItemDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public ItemDto getItem(long userId, long id) {
        log.info("ItemServiceImpl : getItem start with id = {}", id);
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));

        ItemDto itemDto = ItemMapper.mapToItemDto(item);

        Booking nextBooking = bookingRepository.findNextBookingForItemOwner(id,
                        LocalDateTime.now(), userId)
                .orElse(null);
        Booking lastBooking = bookingRepository.findLastBookingForItemOwner(id,
                        LocalDateTime.now(), userId)
                .orElse(null);

        itemDto.setNextBooking(nextBooking != null ? BookingMapper.mapToBookingDto(nextBooking) : null);
        itemDto.setLastBooking(lastBooking != null ? BookingMapper.mapToBookingDto(lastBooking) : null);
        itemDto.setComments(commentRepository.findByItemId(id).stream()
                .map(CommentMapper::mapToCommentDto)
                .collect(Collectors.toList()));
        return itemDto;
    }

    @Override
    public void removeItem(long id) {
        log.info("ItemServiceImpl : removeItem start with id = {}", id);
        itemRepository.deleteById(id);
    }

    @Override
    public CommentDto addComment(long userId, long id, NewCommentRequest request) {
        log.info("ItemServiceImpl : addComment start with userId = {} and id = {} and request = {}", userId, id, request);
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Booking booking = bookingRepository.findByItemIdAndBookerId(id, userId)
                .orElseThrow(() -> new ConditionsNotMetException("Пользователь не брал эту вещь в аренду"));

        log.info("Booking: {}", booking.getDateEnd());
        log.info("LocalDateTime: {}", LocalDateTime.now());

        if (booking.getDateEnd().isAfter(LocalDateTime.now())) {
            throw new ConditionsNotMetException("Срок аренды еще не прошел для пользователя");
        }

        Comment comment = CommentMapper.mapToComment(request);
        comment.setAuthor(user);
        comment.setItem(item);
        comment = commentRepository.save(comment);

        return CommentMapper.mapToCommentDto(comment);
    }
}
