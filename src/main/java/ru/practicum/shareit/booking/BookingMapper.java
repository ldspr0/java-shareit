package ru.practicum.shareit.booking;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingRequest;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.user.UserMapper;

@Mapper(componentModel = "spring", uses = {ItemMapper.class, UserMapper.class})
public interface BookingMapper {

    @Mapping(source = "dateStart", target = "start")
    @Mapping(source = "dateEnd", target = "end")
    @Mapping(source = "bookedTo", target = "booker")
    BookingDto bookingToBookingDto(Booking booking);

    @Mapping(source = "start", target = "dateStart")
    @Mapping(source = "end", target = "dateEnd")
    Booking newBookingRequestToBooking(NewBookingRequest request);


}
