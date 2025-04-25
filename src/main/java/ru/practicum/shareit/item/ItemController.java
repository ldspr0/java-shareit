package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.comments.dto.CommentDto;
import ru.practicum.shareit.item.comments.dto.NewCommentRequest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto createItem(@RequestHeader(USER_ID_HEADER) long userId,
                              @Valid @RequestBody NewItemRequest request) {
        log.info("createItem request with userId = {} and request = {}", userId, request);
        return itemService.createItem(userId, request);
    }

    @PostMapping("/{id}/comment")
    public CommentDto addComment(@RequestHeader(USER_ID_HEADER) long userId,
                                 @PathVariable long id,
                                 @Valid @RequestBody NewCommentRequest request) {
        log.info("addComment request with userId = {} and id = {} and request = {}", userId, id, request);
        return itemService.addComment(userId, id, request);
    }

    @PatchMapping("/{id}")
    public ItemDto updateItem(@RequestHeader(USER_ID_HEADER) long userId,
                              @PathVariable long id,
                              @Valid @RequestBody UpdateItemRequest request) {
        log.info("updateItem request with userId = {} and id = {} and request = {}", userId, id, request);
        return itemService.updateItem(userId, id, request);
    }

    @GetMapping
    public Collection<ItemDto> getAllItems(@RequestHeader(USER_ID_HEADER) long userId) {
        log.info("getAllItems request with userId = {}", userId);
        return itemService.getAllItems(userId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> search(@RequestParam(defaultValue = "") String text) {
        log.info("search request with text = {}", text);
        return itemService.search(text);
    }

    @GetMapping("/{id}")
    public ItemDto getItem(@RequestHeader(USER_ID_HEADER) long userId,
                           @PathVariable long id) {
        log.info("getItem request with userId = {} and id = {} ", userId, id);
        return itemService.getItem(userId, id);
    }

    @DeleteMapping("/{id}")
    public void removeItem(@PathVariable long id) {
        log.info("removeItem request with id = {}", id);
        itemService.removeItem(id);
    }
}
