package ru.practicum.shareit.item.comments;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne()
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @ManyToOne()
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    private String text;

    private LocalDateTime created;
}
