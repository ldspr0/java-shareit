package ru.practicum.shareit.booking;

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
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne()
    @JoinColumn(name = "booked_by_id", referencedColumnName = "id")
    private User bookedTo;

    @Column(name = "start_date")
    private LocalDateTime dateStart;

    @Column(name = "end_date")
    private LocalDateTime dateEnd;

    @Enumerated(EnumType.STRING)
    private Status status;
}