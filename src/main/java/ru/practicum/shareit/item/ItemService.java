package ru.practicum.shareit.item;

import ru.practicum.shareit.item.comments.dto.CommentDto;
import ru.practicum.shareit.item.comments.dto.NewCommentRequest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;

import java.util.Collection;

public interface ItemService {
    ItemDto createItem(long userId, NewItemRequest request);

    ItemDto updateItem(long userId, long id, UpdateItemRequest request);

    Collection<ItemDto> getAllItems(long userId);

    Collection<ItemDto> search(String text);

    ItemDto getItem(long userId, long id);

    void removeItem(long id);

    CommentDto addComment(long userId, long id, NewCommentRequest request);
}
