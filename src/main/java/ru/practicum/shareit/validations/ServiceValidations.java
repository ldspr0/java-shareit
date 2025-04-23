package ru.practicum.shareit.validations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplicatedDataException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceValidations {
    private final UserRepository userRepository;

    public void checkIfEmailIsInUseOrThrowDuplicationError(String email) {
        Optional<User> alreadyExistUser = userRepository.findByEmail(email);
        if (alreadyExistUser.isPresent()) {
            throw new DuplicatedDataException("Email уже используется");
        }
    }

    public void checkIfUserExistsOrThrowError(long userId) {
        Optional<User> userExists = userRepository.findById(userId);
        if (userExists.isEmpty()) {
            throw new NotFoundException("Пользователь с таким Id не найден");
        }
    }

}
