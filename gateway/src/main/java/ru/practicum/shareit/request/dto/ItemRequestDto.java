package ru.practicum.shareit.request.dto;

import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDtoShort;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class ItemRequestDto {
    private long id;

    private long userId;

    private String description;

    private LocalDateTime created;

    private Collection<ItemDtoShort> items;
}
