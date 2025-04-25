package ru.practicum.shareit.item.comments.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewCommentRequest {
    @NotBlank
    @Length(min = 1, max = 1000)
    private String text;
}
