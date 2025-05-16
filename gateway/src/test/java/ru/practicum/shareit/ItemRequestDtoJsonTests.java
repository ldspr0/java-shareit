package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.ItemDtoShort;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ItemRequestDtoJsonTests {

    @Autowired
    private JacksonTester<ItemRequestDto> json;

    @Test
    public void testSerialize() throws Exception {
        LocalDateTime created = LocalDateTime.of(2023, 1, 1, 12, 0);

        ItemDtoShort itemShort = new ItemDtoShort();
        itemShort.setId(1L);
        itemShort.setName("Short Item");

        ItemRequestDto requestDto = new ItemRequestDto();
        requestDto.setId(1L);
        requestDto.setUserId(100L);
        requestDto.setDescription("Test Request");
        requestDto.setCreated(created);
        requestDto.setItems(List.of(itemShort));

        JsonContent<ItemRequestDto> result = json.write(requestDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathNumberValue("$.userId").isEqualTo(100);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("Test Request");
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo("2023-01-01T12:00:00");
        assertThat(result).extractingJsonPathArrayValue("$.items").hasSize(1);
        assertThat(result).extractingJsonPathNumberValue("$.items[0].id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.items[0].name").isEqualTo("Short Item");
    }

    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"id\":1,\"userId\":100,\"description\":\"Test Request\"," +
                "\"created\":\"2023-01-01T12:00:00\"," +
                "\"items\":[{\"id\":1,\"name\":\"Short Item\",\"description\":\"Short Description\"," +
                "\"available\":true,\"requestId\":10}]}";

        ItemRequestDto result = json.parseObject(content);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUserId()).isEqualTo(100L);
        assertThat(result.getDescription()).isEqualTo("Test Request");
        assertThat(result.getCreated()).isEqualTo(LocalDateTime.of(2023, 1, 1, 12, 0));
        assertThat(result.getItems()).hasSize(1);
    }
}