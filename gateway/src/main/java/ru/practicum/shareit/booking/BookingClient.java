package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import ru.practicum.shareit.booking.dto.NewBookingRequest;

import ru.practicum.shareit.client.BaseClient;

import static ru.practicum.shareit.constants.Constants.API_PREFIX_BOOKING;

@Service
public class BookingClient extends BaseClient {


    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX_BOOKING))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> createBooking(long userId, NewBookingRequest requestDto) {
        return post("", userId, requestDto);
    }

    public ResponseEntity<Object> getBooking(long userId, Long bookingId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> getAllBookingsByOwner(long userId, String state) {
        return get("/owner?state=" + state, userId);
    }

    public ResponseEntity<Object> getAllBookingsByUser(long userId, String state) {
        return get("?state=" + state, userId);
    }

    public ResponseEntity<Object> setApproval(long userId, long id, boolean approved) {
        return patch("/" + id + "?approved=" + approved, userId);

    }
}
