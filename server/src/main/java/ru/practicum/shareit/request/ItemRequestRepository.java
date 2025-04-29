package ru.practicum.shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    Collection<ItemRequest> findByUserIdOrderByCreatedDesc(long userId);

    Collection<ItemRequest> findByUserIdNotOrderByCreatedDesc(long userId);
}
