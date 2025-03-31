package ru.practicum.shareit.item;

import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class Item {
    long id;
    long ownerId;

    String name;
    String description;
    String status;

}
