package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserDto {
    private long id;

    private String name;

    @Email
    private String email;
}
