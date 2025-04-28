package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemDtoShort {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long ownerId;

    private String name;

}
