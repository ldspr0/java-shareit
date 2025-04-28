package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.NewItemRequestRequest;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.validations.ServiceValidations;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRepository itemRepository;
    private final ItemRequestRepository itemRequestRepository;
    private final ServiceValidations serviceValidations;


    @Override
    public ItemRequestDto createRequest(long userId, NewItemRequestRequest request) {
        log.info("ItemRequestServiceImpl : createRequest start with userId = {} and request = {}", userId, request);
        serviceValidations.checkIfUserExistsOrThrowError(userId);

        ItemRequest itemRequest = ItemRequestMapper.mapToItemRequest(request);
        itemRequest.setUserId(userId);
        itemRequest = itemRequestRepository.save(itemRequest);

        return ItemRequestMapper.mapToItemRequestDto(itemRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ItemRequestDto> getRequests(long userId) {
        log.info("ItemRequestServiceImpl : getRequests start with userId = {}", userId);

        Collection<ItemRequest> requests = itemRequestRepository.findByUserIdOrderByCreatedDesc(userId);

        return requests.stream()
                .map(request -> {
                    Collection<Item> items = itemRepository.findByRequestId(request.getId());
                    return ItemRequestMapper.mapToItemRequestDto(request, items);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ItemRequestDto> getRequestsOthers(long userId) {
        log.info("ItemRequestServiceImpl : getRequestsOthers start with userId = {}", userId);

        return itemRequestRepository.findByUserIdNotOrderByCreatedDesc(userId).stream()
                .map(ItemRequestMapper::mapToItemRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ItemRequestDto getRequestById(long id) {
        log.info("ItemRequestServiceImpl : getRequestById start with id = {}", id);

        ItemRequest request = itemRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Запрос с ID=" + id + " не найден"));

        Collection<Item> items = itemRepository.findByRequestId(id);

        return ItemRequestMapper.mapToItemRequestDto(request, items);

    }
}
