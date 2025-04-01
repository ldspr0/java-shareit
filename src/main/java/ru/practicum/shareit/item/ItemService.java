package ru.practicum.shareit.item;

public interface ItemService {
    void createItem(Item item);

    void updateItem(long id, Item item);

    void getAllItems();

    void search(String text);

    void getItem(long id);

    void removeItem(long id);
}
