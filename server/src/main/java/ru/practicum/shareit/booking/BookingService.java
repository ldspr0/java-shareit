package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingRequest;

import java.util.Collection;

public interface BookingService {
    Collection<BookingDto> getAllBookingsByOwner(long userId, String state);

    Collection<BookingDto> getAllBookingsByUser(long userId, String state);

    BookingDto getBooking(long userId, long id);

    BookingDto setApproval(long userId, long id, boolean approved);

    BookingDto createBooking(long userId, NewBookingRequest request);
}
