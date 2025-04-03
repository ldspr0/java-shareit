package ru.practicum.shareit.item;

import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class Item {
    private long id;
    private long ownerId;

    private String name;
    private String description;
    private boolean available;

}
