package ru.practicum.shareit.booking;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne()
    @JoinColumn(name = "item_id", referencedColumnName = "id")
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