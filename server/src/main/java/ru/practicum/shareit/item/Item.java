package ru.practicum.shareit.item;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long ownerId;

    private String name;

    private String description;

    @Column(name = "is_available")
    private boolean available;

    private Long requestId;
}
