package ru.practicum.shareit.booking;

import jakarta.persistence.Enumerated;

import java.time.Instant;


public class Booking {
    private long itemId;
    private long bookedTo;

    private Instant dateStart;
    private Instant dateEnd;

    @Enumerated
    private String status;
    private String review;
}
