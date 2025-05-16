package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.comments.dto.CommentDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CommentDtoJsonTests {

    @Autowired
    private JacksonTester<CommentDto> json;

    @Test
    public void testSerialize() throws Exception {
        LocalDateTime created = LocalDateTime.of(2023, 1, 1, 12, 0);

        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setAuthorName("Author");
        commentDto.setText("Test comment");
        commentDto.setCreated(created);

        JsonContent<CommentDto> result = json.write(commentDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.authorName").isEqualTo("Author");
        assertThat(result).extractingJsonPathStringValue("$.text").isEqualTo("Test comment");
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo("2023-01-01T12:00:00");
    }

    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"id\":1,\"authorName\":\"Author\",\"text\":\"Test comment\",\"created\":\"2023-01-01T12:00:00\"}";

        CommentDto result = json.parseObject(content);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getAuthorName()).isEqualTo("Author");
        assertThat(result.getText()).isEqualTo("Test comment");
        assertThat(result.getCreated()).isEqualTo(LocalDateTime.of(2023, 1, 1, 12, 0));
    }
}