package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewItemRequestRequest {
    @NotBlank
    @Length(min = 1, max = 500)
    private String description;
}
