package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.comments.dto.NewCommentRequest;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }


    public ResponseEntity<Object> createItem(long userId, @Valid NewItemRequest request) {
        return post("", userId, request);
    }

    public ResponseEntity<Object> addComment(long userId, long id, @Valid NewCommentRequest request) {
        return post("/" + id + "/comment", userId, request);
    }

    public ResponseEntity<Object> updateItem(long userId, long id, @Valid UpdateItemRequest request) {
        return patch("/" + id, userId, request);
    }

    public ResponseEntity<Object> getAllItems(long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> search(String text) {
        return get("/search?text=" + text);
    }

    public ResponseEntity<Object> getItem(long userId, long id) {
        return get("/" + id, userId);
    }

    public ResponseEntity<Object> removeItem(long id) {
        return delete("/" + id);
    }
}