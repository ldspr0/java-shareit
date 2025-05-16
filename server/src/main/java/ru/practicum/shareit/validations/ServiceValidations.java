package ru.practicum.shareit.validations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplicatedDataException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceValidations {
    private final UserRepository userRepository;

    public void checkIfEmailIsInUseOrThrowDuplicationError(String email) {
        Optional<User> alreadyExistUser = userRepository.findByEmail(email);
        if (alreadyExistUser.isPresent()) {
            log.warn("checkIfEmailIsInUseOrThrowDuplicationError validation: Email уже используется. email = {}", email);
            throw new DuplicatedDataException("Email уже используется");
        }
    }

    public void checkIfUserExistsOrThrowError(long userId) {
        Optional<User> userExists = userRepository.findById(userId);
        if (userExists.isEmpty()) {
            log.warn("checkIfUserExistsOrThrowError validation: Пользователь с таким Id не найден. userId = {}", userId);
            throw new NotFoundException("Пользователь с таким Id не найден");
        }
    }

}
