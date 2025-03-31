package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
public class UserController {

    @PostMapping
    public void createUser(@Valid @RequestBody User user) {

    }


    @PatchMapping("/{id}")
    public void updateUser(@PathVariable long id,
                           @Valid @RequestBody User user) {

    }

    @GetMapping("/{id}")
    public void getUser(@PathVariable long id) {

    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable long id) {

    }

}
