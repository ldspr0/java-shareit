package ru.practicum.shareit.item;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ElementCollection
    @CollectionTable(name = "users", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "owner_id")
    private long ownerId;

    @Column
    private String name;

    @Column
    private String description;

    @Column(name = "is_available")
    private boolean available;

}
