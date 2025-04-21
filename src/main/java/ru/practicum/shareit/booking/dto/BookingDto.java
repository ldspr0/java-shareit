package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class BookingDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long itemId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long bookedTo;

    private Instant dateStart;
    private Instant dateEnd;

    private String status;
    private String review;
}
