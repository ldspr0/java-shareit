package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.comments.dto.CommentDto;

import java.util.List;

@Data
public class ItemDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long ownerId;

    private Long requestId;

    private String name;

    private String description;

    private boolean available;

    private BookingDto lastBooking;

    private BookingDto nextBooking;

    private List<CommentDto> comments;
}
