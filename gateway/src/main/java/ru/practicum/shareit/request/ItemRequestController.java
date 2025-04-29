package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.NewItemRequestRequest;

import static ru.practicum.shareit.constants.Constants.API_PREFIX_REQUESTS;
import static ru.practicum.shareit.constants.Constants.USER_ID_HEADER;


@Slf4j
@RestController
@RequestMapping(API_PREFIX_REQUESTS)
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createRequest(@RequestHeader(USER_ID_HEADER) long userId,
                                                @Valid @RequestBody NewItemRequestRequest request) {
        log.info("createRequest with userId = {}, content = {}", userId, request);
        return itemRequestClient.createRequest(userId, request);
    }

    @GetMapping
    public ResponseEntity<Object> getRequests(@RequestHeader(USER_ID_HEADER) long userId) {
        log.info("getRequests request with userId = {}", userId);
        return itemRequestClient.getRequests(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getRequestsOthers(@RequestHeader(USER_ID_HEADER) long userId) {
        log.info("getRequestsOthers request with userId = {}", userId);
        return itemRequestClient.getRequestsOthers(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRequestById(@PathVariable long id) {
        log.info("getRequests request with id = {}", id);
        return itemRequestClient.getRequestById(id);
    }
}
