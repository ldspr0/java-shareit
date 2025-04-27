package ru.practicum.shareit.item.comments.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.practicum.shareit.item.Item;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Item item;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String authorName;

    private String text;

    private LocalDateTime created;
}
