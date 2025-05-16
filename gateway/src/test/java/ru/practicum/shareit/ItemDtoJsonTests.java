package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.comments.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ItemDtoJsonTests {

    @Autowired
    private JacksonTester<ItemDto> json;

    @Test
    public void testSerialize() throws Exception {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setOwnerId(100L);
        itemDto.setRequestId(10L);
        itemDto.setName("Test Item");
        itemDto.setDescription("Test Description");
        itemDto.setAvailable(true);

        CommentDto comment = new CommentDto();
        comment.setId(1L);
        comment.setAuthorName("Comment Author");
        comment.setText("Test comment");
        comment.setCreated(LocalDateTime.of(2023, 1, 1, 12, 0));

        itemDto.setComments(List.of(comment));

        JsonContent<ItemDto> result = json.write(itemDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathNumberValue("$.ownerId").isEqualTo(100);
        assertThat(result).extractingJsonPathNumberValue("$.requestId").isEqualTo(10);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("Test Item");
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("Test Description");
        assertThat(result).extractingJsonPathBooleanValue("$.available").isTrue();
        assertThat(result).extractingJsonPathArrayValue("$.comments").hasSize(1);
        assertThat(result).extractingJsonPathNumberValue("$.comments[0].id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.comments[0].authorName").isEqualTo("Comment Author");
    }

    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"id\":1,\"ownerId\":100,\"requestId\":10,\"name\":\"Test Item\"," +
                "\"description\":\"Test Description\",\"available\":true," +
                "\"comments\":[{\"id\":1,\"authorName\":\"Comment Author\",\"text\":\"Test comment\"," +
                "\"created\":\"2023-01-01T12:00:00\"}]}";

        ItemDto result = json.parseObject(content);

        assertThat(result.getName()).isEqualTo("Test Item");
        assertThat(result.getDescription()).isEqualTo("Test Description");
        assertThat(result.isAvailable()).isTrue();
        assertThat(result.getComments()).hasSize(1);

    }
}