package ru.practicum.shareit.user.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserDto;

@UtilityClass
public class UserMapper {
    public User mapToUser(NewUserRequest request) {
        User user = new User();
        user.setUsername(request.getName());
        user.setEmail(request.getEmail());

        return user;
    }

    public UserDto mapToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public User updateUserFields(User user, UpdateUserRequest request) {
        if (request.hasEmail()) {
            user.setEmail(request.getEmail());
        }
        if (request.hasUsername()) {
            user.setUsername(request.getName());
        }
        return user;
    }
}
