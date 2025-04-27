package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Collection<Item> findByOwnerId(long userId);

    Collection<Item> findByNameContainingIgnoreCaseAndAvailableIsTrueOrDescriptionContainingIgnoreCaseAndAvailableIsTrue(String name, String description);
}
