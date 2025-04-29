package ru.practicum.shareit.item.comments.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.comments.Comment;
import ru.practicum.shareit.item.comments.dto.CommentDto;
import ru.practicum.shareit.item.comments.dto.NewCommentRequest;

import java.time.LocalDateTime;

@UtilityClass
public class CommentMapper {
    public Comment mapToComment(NewCommentRequest request) {
        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setCreated(LocalDateTime.now());
        return comment;
    }

    public CommentDto mapToCommentDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setAuthorName(comment.getAuthor().getUsername());
        dto.setItem(comment.getItem());
        dto.setText(comment.getText());
        dto.setCreated(comment.getCreated());
        return dto;
    }
}
