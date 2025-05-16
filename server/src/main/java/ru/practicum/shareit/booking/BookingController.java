package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingRequest;

import java.util.Collection;

import static ru.practicum.shareit.constants.Constants.API_PREFIX_BOOKING;
import static ru.practicum.shareit.constants.Constants.USER_ID_HEADER;

@Slf4j
@RestController
@RequestMapping(API_PREFIX_BOOKING)
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto createBooking(@RequestHeader(USER_ID_HEADER) long userId,
                                    @RequestBody NewBookingRequest request) {
        log.info("createBooking request with userId = {} and request = {}", userId, request);
        return bookingService.createBooking(userId, request);
    }

    @PatchMapping("/{id}")
    public BookingDto bookingApproval(@RequestHeader(USER_ID_HEADER) long userId,
                                      @PathVariable long id,
                                      @RequestParam(defaultValue = "false") boolean approved) {
        log.info("bookingApproval request with userId = {} and id = {} and approved = {}", userId, id, approved);
        return bookingService.setApproval(userId, id, approved);
    }

    @GetMapping("/{id}")
    public BookingDto getBooking(@RequestHeader(USER_ID_HEADER) long userId,
                                 @PathVariable long id) {
        log.info("getBooking request with userId = {} and id = {}", userId, id);
        return bookingService.getBooking(userId, id);
    }

    @GetMapping
    public Collection<BookingDto> getAllBookingsByUser(@RequestHeader(USER_ID_HEADER) long userId,
                                                       @RequestParam(defaultValue = "ALL") String state) {
        log.info("getAllBookingsByUser request with userId = {} and state = {}", userId, state);
        return bookingService.getAllBookingsByUser(userId, state);
    }

    @GetMapping("/owner")
    public Collection<BookingDto> getAllBookingsByOwner(@RequestHeader(USER_ID_HEADER) long userId,
                                                        @RequestParam(defaultValue = "ALL") String state) {
        log.info("getAllBookingsByOwner request with userId = {} and state = {}", userId, state);
        return bookingService.getAllBookingsByOwner(userId, state);
    }
}