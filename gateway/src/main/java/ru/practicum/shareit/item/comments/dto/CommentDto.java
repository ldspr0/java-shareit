package ru.practicum.shareit.item.comments.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private long id;

    private String authorName;

    private String text;

    private LocalDateTime created;
}
