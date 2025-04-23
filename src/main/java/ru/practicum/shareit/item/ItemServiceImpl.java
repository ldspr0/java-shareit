package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConditionsNotMetException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.validations.ServiceValidations;

import java.util.Collection;
import java.util.Collections;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ServiceValidations serviceValidations;
    private final ItemRepository itemRepository;

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

        return ItemMapper.mapToItemDto(item);
    }

    @Override
    public void removeItem(long id) {
        itemRepository.deleteById(id);
    }
}
