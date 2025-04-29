package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserDto;

import static ru.practicum.shareit.constants.Constants.API_PREFIX_USERS;

@Slf4j
@RestController
@RequestMapping(API_PREFIX_USERS)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody NewUserRequest request) {
        log.info("createUser request with request = {}", request);
        return userService.createUser(request);
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable long id,
                              @RequestBody UpdateUserRequest request) {
        log.info("updateUser request with id = {} and request = {}", id, request);
        return userService.updateUser(id, request);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable long id) {
        log.info("getUser request with id = {}", id);
        return userService.getUser(id);
    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable long id) {
        log.info("removeUser request with id = {}", id);
        userService.removeUser(id);
    }

}
