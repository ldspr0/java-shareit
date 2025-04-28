package ru.practicum.shareit.booking.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingRequest;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.user.mapper.UserMapper;

@UtilityClass
public class BookingMapper {

    public Booking mapToBooking(NewBookingRequest request) {
        Booking booking = new Booking();
        booking.setDateStart(request.getStart());
        booking.setDateEnd(request.getEnd());

        Item item = new Item();
        item.setId(request.getItemId());
        return booking;
    }

    public BookingDto mapToBookingDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setItem(ItemMapper.mapToItemDto(booking.getItem()));
        dto.setStatus(booking.getStatus());
        dto.setBooker(UserMapper.mapToUserDto(booking.getBookedTo()));
        dto.setStart(booking.getDateStart());
        dto.setEnd(booking.getDateEnd());
        return dto;
    }
}
