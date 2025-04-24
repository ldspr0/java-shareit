package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.exception.ConditionsNotMetException;
import ru.practicum.shareit.exception.NotFoundException;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
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

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ServiceValidations serviceValidations;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public ItemDto createItem(long userId, NewItemRequest request) {
        serviceValidations.checkIfUserExistsOrThrowError(userId);

        Item item = ItemMapper.mapToItem(request);
        item.setOwnerId(userId);
        item = itemRepository.save(item);

        return ItemMapper.mapToItemDto(item);
    }

    @Override
    public ItemDto updateItem(long userId, long id, UpdateItemRequest request) {
        serviceValidations.checkIfUserExistsOrThrowError(userId);

        Item updatedItem = itemRepository.findById(id)
                .map(item -> ItemMapper.updateItemFields(item, request))
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));
        if (updatedItem.getOwnerId() != userId) {
            throw new ConditionsNotMetException("Изменять данные о предмете может только владелец");
        }

        updatedItem = itemRepository.save(updatedItem);
        return ItemMapper.mapToItemDto(updatedItem);
    }

    @Override
    public Collection<ItemDto> getAllItems(long userId) {
        return itemRepository.findByOwnerId(userId).stream()
                .map(ItemMapper::mapToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> search(String text) {
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        return itemRepository.findByNameContainingIgnoreCaseAndAvailableIsTrueOrDescriptionContainingIgnoreCaseAndAvailableIsTrue(text, text).stream()
                .map(ItemMapper::mapToItemDto)
                .collect(Collectors.toList());
    }


    @Override
    public ItemDto getItem(long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));

        ItemDto itemDto = ItemMapper.mapToItemDto(item);

        Booking nextBooking = bookingRepository.findFirstByItemIdAndDateStartAfterAndStatusOrderByDateStartAsc(id,
                        LocalDateTime.now(), Status.APPROVED)
                .orElse(null);
        Booking lastBooking = bookingRepository.findFirstByItemIdAndDateEndBeforeAndStatusOrderByDateEndDesc(id,
                        LocalDateTime.now(), Status.APPROVED)
                .orElse(null);

        itemDto.setNextBooking(nextBooking != null ? BookingMapper.mapToBookingDto(nextBooking) : null);
        itemDto.setLastBooking(lastBooking != null ? BookingMapper.mapToBookingDto(lastBooking) : null);
        itemDto.setComments(commentRepository.findByItemId(id).stream().map(CommentMapper::mapToCommentDto).collect(Collectors.toList()));
        return itemDto;
    }

    @Override
    public void removeItem(long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public CommentDto addComment(long userId, long id, NewCommentRequest request) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Booking booking = bookingRepository.findByItemIdAndBookerId(id, userId)
                .orElseThrow(() -> new ConditionsNotMetException("Пользователь не брал эту вещь в аренду"));

        Comment comment = CommentMapper.mapToComment(request);
        comment.setAuthor(user);
        comment.setItem(item);
        comment = commentRepository.save(comment);

        return CommentMapper.mapToCommentDto(comment);
    }
}
