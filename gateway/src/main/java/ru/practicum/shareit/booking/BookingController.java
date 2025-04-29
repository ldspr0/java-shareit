package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final BookingClient bookingClient;

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllBookingsByOwner(@RequestHeader(USER_ID_HEADER) long userId,
                                                        @RequestParam(defaultValue = "ALL") String state) {
        log.info("getAllBookingsByOwner request with userId = {} and state = {}", userId, state);
        return bookingClient.getAllBookingsByOwner(userId, state);
    }

    @GetMapping
    public ResponseEntity<Object> getAllBookingsByUser(@RequestHeader(USER_ID_HEADER) long userId,
                                                       @RequestParam(defaultValue = "ALL") String state) {
        log.info("getAllBookingsByUser request with userId = {} and state = {}", userId, state);
        return bookingClient.getAllBookingsByUser(userId, state);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createBooking(@RequestHeader(USER_ID_HEADER) long userId,
                                                @Valid @RequestBody NewBookingRequest request) {
        log.info("createBooking request with userId = {} and request = {}", userId, request);
        return bookingClient.createBooking(userId, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBooking(@RequestHeader(USER_ID_HEADER) long userId,
                                             @PathVariable Long id) {
        log.info("getBooking request with userId = {} and id = {}", userId, id);
        return bookingClient.getBooking(userId, id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> bookingApproval(@RequestHeader(USER_ID_HEADER) long userId,
                                                  @PathVariable long id,
                                                  @RequestParam(defaultValue = "false") boolean approved) {
        log.info("bookingApproval request with userId = {} and id = {} and approved = {}", userId, id, approved);
        return bookingClient.setApproval(userId, id, approved);
    }

}