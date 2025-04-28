package ru.practicum.shareit.request.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDtoShort;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class ItemRequestDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    private long userId;

    private String description;

    private LocalDateTime created;

    Collection<ItemDtoShort> items;
}
