package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.comments.dto.NewCommentRequest;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemClient itemClient;
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createItem(@RequestHeader(USER_ID_HEADER) long userId,
                                             @Valid @RequestBody NewItemRequest request) {
        log.info("createItem request with userId = {} and request = {}", userId, request);
        return itemClient.createItem(userId, request);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader(USER_ID_HEADER) long userId,
                                             @PathVariable long id,
                                             @Valid @RequestBody NewCommentRequest request) {
        log.info("addComment request with userId = {} and id = {} and request = {}", userId, id, request);
        return itemClient.addComment(userId, id, request);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateItem(@RequestHeader(USER_ID_HEADER) long userId,
                                             @PathVariable long id,
                                             @Valid @RequestBody UpdateItemRequest request) {
        log.info("updateItem request with userId = {} and id = {} and request = {}", userId, id, request);
        return itemClient.updateItem(userId, id, request);
    }

    @GetMapping
    public ResponseEntity<Object> getAllItems(@RequestHeader(USER_ID_HEADER) long userId) {
        log.info("getAllItems request with userId = {}", userId);
        return itemClient.getAllItems(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam(defaultValue = "") String text) {
        log.info("search request with text = {}", text);
        return itemClient.search(text);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItem(@RequestHeader(USER_ID_HEADER) long userId, @PathVariable long id) {
        log.info("getItem request with userId = {} and id = {}", userId, id);
        return itemClient.getItem(userId, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeItem(@PathVariable long id) {
        log.info("removeItem request with id = {}", id);
        return itemClient.removeItem(id);
    }
}
