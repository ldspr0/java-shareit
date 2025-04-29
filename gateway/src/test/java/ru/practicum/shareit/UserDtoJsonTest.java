package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class UserDtoJsonTest {

    @Autowired
    private JacksonTester<UserDto> json;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testSerialize() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Test User");
        userDto.setEmail("test@example.com");

        JsonContent<UserDto> result = json.write(userDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("Test User");
        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo("test@example.com");
        assertThat(result).doesNotHaveJsonPath("$.password"); // проверка READ_ONLY поля
    }

    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"id\":1,\"name\":\"Test User\",\"email\":\"test@example.com\"}";

        UserDto result = json.parseObject(content);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Test User");
        assertThat(result.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void whenEmailIsInvalid_thenValidationFails() {
        UserDto userDto = new UserDto();
        userDto.setEmail("invalid-email");

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertThat(violations).isNotEmpty();
    }
}