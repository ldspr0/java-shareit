
package ru.practicum.shareit.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.validations.ServiceValidations;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ServiceValidations serviceValidations;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(NewUserRequest request) {
        log.info("UserServiceImpl : createUser start with request = {}", request);
        serviceValidations.checkIfEmailIsInUseOrThrowDuplicationError(request.getEmail());

        User user = userMapper.newUserRequestToUser(request);
        user = userRepository.save(user);

        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto updateUser(long id, UpdateUserRequest request) {
        log.info("UserServiceImpl : updateUser start with request = {}", request);
        serviceValidations.checkIfEmailIsInUseOrThrowDuplicationError(request.getEmail());

        User updatedUser = userRepository.findById(id)
                .map(user -> userMapper.updateUserFromRequest(request, user))
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return userMapper.userToUserDto(updatedUser);
    }

    @Override
    public UserDto getUser(long id) {
        log.info("UserServiceImpl : getUser start with id = {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        return userMapper.userToUserDto(user);
    }

    @Override
    public void removeUser(long id) {
        log.info("UserServiceImpl : removeUser start with id = {}", id);
        userRepository.deleteById(id);
    }
}
