package ru.practicum.shareit.item.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemDtoShort;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.dto.ItemDto;

@UtilityClass
public class ItemMapper {
    public Item mapToItem(NewItemRequest request) {
        Item item = new Item();
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setAvailable(request.getAvailable());
        item.setRequestId(request.getRequestId());
        return item;
    }

    public ItemDto mapToItemDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setOwnerId(item.getOwnerId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setAvailable(item.isAvailable());
        dto.setRequestId(item.getRequestId());
        return dto;
    }

    public ItemDtoShort mapToItemDtoShort(Item item) {
        ItemDtoShort dto = new ItemDtoShort();
        dto.setId(item.getId());
        dto.setOwnerId(item.getOwnerId());
        dto.setName(item.getName());
        return dto;
    }

    public Item updateItemFields(Item item, UpdateItemRequest request) {
        if (request.hasDescription()) {
            item.setDescription(request.getDescription());
        }
        if (request.hasName()) {
            item.setName(request.getName());
        }
        item.setAvailable(request.isAvailable());
        return item;
    }
}
