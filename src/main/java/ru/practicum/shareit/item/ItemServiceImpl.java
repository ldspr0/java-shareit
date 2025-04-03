package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConditionsNotMetException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserDao;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemDao itemDao;
    private final UserDao userDao;

    @Override
    public ItemDto createItem(long userId, NewItemRequest request) {
        Optional<User> userExists = userDao.getUser(userId);
        if (userExists.isEmpty()) {
            throw new NotFoundException("Пользователь с таким Id не найден");
        }

        Item item = ItemMapper.mapToItem(request);
        item.setOwnerId(userId);
        item = itemDao.createItem(item);

        return ItemMapper.mapToItemDto(item);
    }

    @Override
    public ItemDto updateItem(long userId, long id, UpdateItemRequest request) {
        Optional<User> userExists = userDao.getUser(userId);
        if (userExists.isEmpty()) {
            throw new NotFoundException("Пользователь с таким Id не найден");
        }

        Item updatedItem = itemDao.getItem(id)
                .map(item -> ItemMapper.updateItemFields(item, request))
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));
        if (updatedItem.getOwnerId() != userId) {
            throw new ConditionsNotMetException("Изменять данные о предмете может только владелец");
        }

        updatedItem = itemDao.updateItem(updatedItem);
        return ItemMapper.mapToItemDto(updatedItem);
    }

    @Override
    public Collection<ItemDto> getAllItems(long userId) {
        return itemDao.getAllByUser(userId).stream()
                .map(ItemMapper::mapToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> search(String text) {
        return itemDao.getItemsBySearchText(text).stream()
                .map(ItemMapper::mapToItemDto)
                .collect(Collectors.toList());
    }


    @Override
    public ItemDto getItem(long id) {
        Item item = itemDao.getItem(id)
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));

        return ItemMapper.mapToItemDto(item);
    }

    @Override
    public boolean removeItem(long id) {
        return itemDao.removeItem(id);
    }
}
