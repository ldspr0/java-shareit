package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewItemRequest {
    @NotNull
    @Length(min = 1, max = 200)
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Boolean available;
}
