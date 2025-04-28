package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.NewItemRequestRequest;

import java.util.Collection;

public interface ItemRequestService {
    ItemRequestDto createRequest(long userId, NewItemRequestRequest request);

    Collection<ItemRequestDto> getRequests(long userId);

    Collection<ItemRequestDto> getRequestsOthers(long userId);

    ItemRequestDto getRequestById(long id);
}
