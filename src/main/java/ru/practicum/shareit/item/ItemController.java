package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    @PostMapping
    public void createItem(@Valid @RequestBody Item item) {

    }

    @PatchMapping("/{id}")
    public void updateItem(@PathVariable long id,
                           @Valid @RequestBody Item item) {

    }

    @GetMapping
    public void getAllItems() {

    }

    @GetMapping("/search")
    public void search(@RequestParam(defaultValue = "") int text) {

    }

    @GetMapping("/{id}")
    public void getItem(@PathVariable long id) {

    }

    @DeleteMapping("/{id}")
    public void removeItem(@PathVariable long id) {

    }
}
