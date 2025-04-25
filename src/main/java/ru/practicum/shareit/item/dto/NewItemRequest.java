package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewItemRequest {
    @NotBlank
    @Length(min = 1, max = 255)
    private String name;

    @NotBlank
    private String description;
    
    @NotNull
    private Boolean available;
}
