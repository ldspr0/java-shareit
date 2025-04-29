package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.NewItemRequestRequest;

import java.util.Collection;

import static ru.practicum.shareit.constants.Constants.API_PREFIX_REQUESTS;
import static ru.practicum.shareit.constants.Constants.USER_ID_HEADER;

@Slf4j
@RestController
@RequestMapping(API_PREFIX_REQUESTS)
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemRequestDto createRequest(@RequestHeader(USER_ID_HEADER) long userId,
                                        @RequestBody NewItemRequestRequest request) {
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
