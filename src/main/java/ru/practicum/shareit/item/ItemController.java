package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    /*
    Добавление новой вещи. Будет происходить по эндпоинту POST /items.
    На вход поступает объект ItemDto. userId в заголовке X-Sharer-User-Id — это идентификатор пользователя,
    который добавляет вещь. Именно этот пользователь — владелец вещи.
    Идентификатор владельца будет поступать на вход в каждом из запросов, рассмотренных далее.
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createItem(@Valid @RequestBody Item item) {
        itemService.createItem(item);
    }

    /*
    Редактирование вещи. Эндпоинт PATCH /items/{itemId}.
    Изменить можно название, описание и статус доступа к аренде.
    Редактировать вещь может только её владелец.
     */
    @PatchMapping("/{id}")
    public void updateItem(@PathVariable long id,
                           @Valid @RequestBody Item item) {
        itemService.updateItem(id, item);
    }

    /*
    Просмотр владельцем списка всех его вещей с указанием названия и описания для каждой из них.
     */
    @GetMapping
    public void getAllItems() {
        itemService.getAllItems();
    }

    /*
    Поиск вещи потенциальным арендатором.
    Пользователь передаёт в строке запроса текст, и система ищет вещи, содержащие этот текст в названии или описании.
    Происходит по эндпоинту /items/search?text={text}, в text передаётся текст для поиска.
    Проверьте, что поиск возвращает только доступные для аренды вещи.
     */
    @GetMapping("/search")
    public void search(@RequestParam(defaultValue = "") String text) {
        itemService.search(text);
    }

    @GetMapping("/{id}")
    public void getItem(@PathVariable long id) {
        itemService.getItem(id);
    }

    @DeleteMapping("/{id}")
    public void removeItem(@PathVariable long id) {
        itemService.removeItem(id);
    }
}
