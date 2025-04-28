package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.NewBookingRequest;


@Slf4j
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingClient bookingClient;
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";


    //    @GetMapping
//    public ResponseEntity<Object> getAllBookingsByUser(@RequestHeader(USER_ID_HEADER) long userId,
//                                                       @RequestParam(defaultValue = "ALL") String state) {
//        log.info("getAllBookingsByUser request with userId = {} and state = {}", userId, state);
//        return bookingClient.getAllBookingsByUser(userId, state);
//    }
//
    @GetMapping("/owner")
    public ResponseEntity<Object> getAllBookingsByOwner(@RequestHeader(USER_ID_HEADER) long userId,
                                                        @RequestParam(defaultValue = "ALL") String state) {
        log.info("getAllBookingsByOwner request with userId = {} and state = {}", userId, state);
        return bookingClient.getAllBookingsByOwner(userId, state);
    }

    @GetMapping
    public ResponseEntity<Object> getBookings(@RequestHeader(USER_ID_HEADER) long userId,
                                              @RequestParam(name = "state", defaultValue = "all") String stateParam,
                                              @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                              @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        State state = State.from(stateParam)
                .orElseThrow(() -> new IllegalArgumentException("Unknown state: " + stateParam));
        log.info("Get booking with state {}, userId={}, from={}, size={}", stateParam, userId, from, size);
        return bookingClient.getBookings(userId, state, from, size);
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