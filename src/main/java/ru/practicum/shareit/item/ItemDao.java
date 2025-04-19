package ru.practicum.shareit.item;

import java.util.Collection;
import java.util.Optional;

public interface ItemDao {
    Collection<Item> getAllByUser(long userId);

    Item createItem(Item item);

    Optional<Item> getItem(long id);

    Collection<Item> getItemsBySearchText(String text);

    Item updateItem(Item item);

    boolean removeItem(long id);
}
