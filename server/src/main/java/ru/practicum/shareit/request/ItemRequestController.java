package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.NewItemRequestRequest;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestService itemRequestService;
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemRequestDto createRequest(@RequestHeader(USER_ID_HEADER) long userId,
                                        @Valid @RequestBody NewItemRequestRequest request) {
        log.info("createRequest with userId = {}, content = {}", userId, request);
        return itemRequestService.createRequest(userId, request);
    }

    @GetMapping
    public Collection<ItemRequestDto> getRequests(@RequestHeader(USER_ID_HEADER) long userId) {
        log.info("getRequests request with userId = {}", userId);
        return itemRequestService.getRequests(userId);
    }

    @GetMapping("/all")
    public Collection<ItemRequestDto> getRequestsOthers(@RequestHeader(USER_ID_HEADER) long userId) {
        log.info("getRequestsOthers request with userId = {}", userId);
        return itemRequestService.getRequestsOthers(userId);
    }

    @GetMapping("/{id}")
    public ItemRequestDto getRequestById(@PathVariable long id) {
        log.info("getRequests request with id = {}", id);
        return itemRequestService.getRequestById(id);
    }
}
