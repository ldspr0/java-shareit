package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplicatedDataException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public UserDto createUser(NewUserRequest request) {

        Optional<User> alreadyExistUser = userDao.getUserByEmail(request.getEmail());
        if (alreadyExistUser.isPresent()) {
            throw new DuplicatedDataException("Email уже используется");
        }

        User user = UserMapper.mapToUser(request);

        user = userDao.createUser(user);

        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto updateUser(long id, UpdateUserRequest request) {
        Optional<User> alreadyExistUser = userDao.getUserByEmail(request.getEmail());
        if (alreadyExistUser.isPresent()) {
            throw new DuplicatedDataException("Email уже используется");
        }

        User updatedUser = userDao.getUser(id)
                .map(user -> UserMapper.updateUserFields(user, request))
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        updatedUser = userDao.updateUser(updatedUser);
        return UserMapper.mapToUserDto(updatedUser);
    }

    @Override
    public UserDto getUser(long id) {
        User user = userDao.getUser(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        return UserMapper.mapToUserDto(user);
    }

    @Override
    public boolean removeUser(long id) {
        return false;
    }
}
