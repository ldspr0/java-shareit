package ru.practicum.shareit.request.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.NewItemRequestRequest;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@UtilityClass
public class ItemRequestMapper {
    public ItemRequest mapToItemRequest(NewItemRequestRequest request) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription(request.getDescription());
        itemRequest.setCreated(LocalDateTime.now());
        return itemRequest;
    }

    public ItemRequestDto mapToItemRequestDto(ItemRequest itemRequest) {
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(itemRequest.getId());
        dto.setDescription(itemRequest.getDescription());
        dto.setUserId(itemRequest.getUserId());
        dto.setCreated(itemRequest.getCreated());
        return dto;
    }

    public ItemRequestDto mapToItemRequestDto(ItemRequest itemRequest, Collection<Item> items) {
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(itemRequest.getId());
        dto.setDescription(itemRequest.getDescription());
        dto.setUserId(itemRequest.getUserId());
        dto.setCreated(itemRequest.getCreated());

        // Преобразуем связанные Items в ItemDto
        if (items != null) {
            dto.setItems(items.stream()
                    .map(ItemMapper::mapToItemDtoShort)
                    .collect(Collectors.toList()));
        } else {
            dto.setItems(Collections.emptyList());
        }

        return dto;
    }
}
