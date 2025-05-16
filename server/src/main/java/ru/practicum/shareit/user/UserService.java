package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {
    UserDto createUser(NewUserRequest request);

    UserDto updateUser(long id, UpdateUserRequest request);

    UserDto getUser(long id);

    void removeUser(long id);
}
