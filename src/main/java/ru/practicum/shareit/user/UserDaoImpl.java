package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class UserDaoImpl implements UserDao {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Collection<User> getAll() {
        log.trace("get all users from Memory: {}", users.values());
        return List.copyOf(users.values());
    }

    @Override
    public User createUser(User user) {
        log.info("try to save user in Memory: {}", user.toString());
        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("user is saved in Memory: {}", user);
        return user;
    }

    @Override
    public Optional<User> getUser(long id) {
        return Optional.of(users.get(id));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return getAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public User updateUser(User user) {
        log.info("try to update user in Memory: {}", user.toString());
        User oldUser = users.get(user.getId());
        oldUser.setEmail(user.getEmail());
        oldUser.setUsername(user.getUsername());
        log.info("user is updated in Memory: {}", user);
        return user;
    }

    @Override
    public boolean removeUser(long id) {
        if (getUser(id).isEmpty()) {
            return false;
        }
        users.remove(id);
        log.info("user with id {} is removed from Memory", id);
        return true;
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}

