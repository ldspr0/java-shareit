package ru.practicum.shareit.user;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {
    Collection<User> getAll();

    User createUser(User user);

    Optional<User> getUser(long id);

    Optional<User> getUserByEmail(String email);

    User updateUser(User user);

    boolean removeUser(long id);
}