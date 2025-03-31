package ru.practicum.shareit.user;

public interface UserService {
    void createUser(User user);

    void updateUser(long id, User user);

    void getUser(long id);

    void removeUser(long id);
}
