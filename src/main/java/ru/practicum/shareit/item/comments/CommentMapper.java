package ru.practicum.shareit.item.comments;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.item.comments.dto.CommentDto;
import ru.practicum.shareit.item.comments.dto.NewCommentRequest;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "author.username", target = "authorName")
    CommentDto commentToCommentDto(Comment comment);

    @Mapping(target = "created", expression = "java(java.time.LocalDateTime.now())")
    Comment newCommentRequestToComment(NewCommentRequest request);
}
