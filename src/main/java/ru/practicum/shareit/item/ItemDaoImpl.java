package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ItemDaoImpl implements ItemDao {
    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public Collection<Item> getAllByUser(long userId) {
        log.trace("get items from Memory for userId: {}", userId);
        return items.values().stream()
                .filter(item -> item.getOwnerId() == userId)
                .collect(Collectors.toSet());
    }

    @Override
    public Item createItem(Item item) {
        log.info("try to save item in Memory: {}", item.toString());
        item.setId(getNextId());
        items.put(item.getId(), item);
        log.info("item is saved in Memory: {}", item);
        return item;
    }

    @Override
    public Optional<Item> getItem(long id) {
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public Collection<Item> getItemsBySearchText(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }

        String searchText = text.toLowerCase();

        return items.values().stream()
                .filter(Item::isAvailable)
                .filter(item ->
                        item.getName().toLowerCase().contains(searchText) ||
                        item.getDescription().toLowerCase().contains(searchText)
                )
                .collect(Collectors.toList());
    }

    @Override
    public Item updateItem(Item item) {
        log.info("try to update item in Memory: {}", item.toString());
        Item oldItem = items.get(item.getId());
        oldItem.setDescription(item.getDescription());
        oldItem.setName(item.getName());
        log.info("item is updated in Memory: {}", item);
        return item;
    }

    @Override
    public boolean removeItem(long id) {
        if (getItem(id).isEmpty()) {
            return false;
        }
        items.remove(id);
        log.info("item with id {} is removed from Memory", id);
        return true;
    }

    private long getNextId() {
        long currentMaxId = items.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
