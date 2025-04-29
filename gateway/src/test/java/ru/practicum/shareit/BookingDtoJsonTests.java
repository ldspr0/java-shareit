package ru.practicum.shareit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookingDtoJsonTests {

    @Autowired
    private JacksonTester<BookingDto> json;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerialize() throws Exception {
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2023, 1, 2, 10, 0);

        ItemDto item = new ItemDto();
        item.setId(1L);
        item.setName("Test Item");

        UserDto booker = new UserDto();
        booker.setId(1L);
        booker.setName("Test User");

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        bookingDto.setItem(item);
        bookingDto.setBooker(booker);
        bookingDto.setStart(start);
        bookingDto.setEnd(end);
        bookingDto.setStatus(Status.WAITING);

        JsonContent<BookingDto> result = json.write(bookingDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathNumberValue("$.item.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.item.name").isEqualTo("Test Item");
        assertThat(result).extractingJsonPathNumberValue("$.booker.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.booker.name").isEqualTo("Test User");
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo("2023-01-01T10:00:00");
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo("2023-01-02T10:00:00");
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo("WAITING");
    }

    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"id\":1,\"item\":{\"id\":1,\"name\":\"Test Item\"}," +
                "\"booker\":{\"id\":1,\"name\":\"Test User\"}," +
                "\"start\":\"2023-01-01T10:00:00\"," +
                "\"end\":\"2023-01-02T10:00:00\"," +
                "\"status\":\"WAITING\"}";

        BookingDto result = json.parseObject(content);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getItem().getName()).isEqualTo("Test Item");
        assertThat(result.getBooker().getId()).isEqualTo(1L);
        assertThat(result.getBooker().getName()).isEqualTo("Test User");
        assertThat(result.getStart()).isEqualTo(LocalDateTime.of(2023, 1, 1, 10, 0));
        assertThat(result.getEnd()).isEqualTo(LocalDateTime.of(2023, 1, 2, 10, 0));
        assertThat(result.getStatus()).isEqualTo(Status.WAITING);
    }

    @Test
    public void testSerializeWithNullFields() throws Exception {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);

        JsonContent<BookingDto> result = json.write(bookingDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathValue("$.item").isNull();
        assertThat(result).extractingJsonPathValue("$.booker").isNull();
        assertThat(result).extractingJsonPathValue("$.start").isNull();
        assertThat(result).extractingJsonPathValue("$.end").isNull();
        assertThat(result).extractingJsonPathValue("$.status").isNull();
    }
}