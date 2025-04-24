package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingRequest;

import java.util.Collection;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto createBooking(@RequestHeader(USER_ID_HEADER) long userId,
                                    @Valid @RequestBody NewBookingRequest request) {
        return bookingService.createBooking(userId, request);
    }

    @PatchMapping("/{id}")
    public BookingDto bookingApproval(@RequestHeader(USER_ID_HEADER) long userId,
                                      @PathVariable long id,
                                      @RequestParam(defaultValue = "false") boolean approved) {
        return bookingService.setApproval(userId, id, approved);
    }

    @GetMapping("/{id}")
    public BookingDto getBooking(@RequestHeader(USER_ID_HEADER) long userId,
                                 @PathVariable long id) {
        return bookingService.getBooking(userId, id);
    }


    @GetMapping
    public Collection<BookingDto> getAllBookingsByUser(@RequestHeader(USER_ID_HEADER) long userId,
                                                       @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getAllBookingsByUser(userId, state);
    }

    @GetMapping("/owner")
    public Collection<BookingDto> getAllBookingsByOwner(@RequestHeader(USER_ID_HEADER) long userId,
                                                        @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getAllBookingsByOwner(userId, state);
    }
}