package ru.practicum.shareit.request;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.request.dto.NewItemRequestRequest;

import static ru.practicum.shareit.constants.Constants.API_PREFIX_REQUESTS;

@Service
public class ItemRequestClient extends BaseClient {


    @Autowired
    public ItemRequestClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX_REQUESTS))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }


    public ResponseEntity<Object> createRequest(long userId, @Valid NewItemRequestRequest request) {
        return post("", userId, request);
    }

    public ResponseEntity<Object> getRequests(long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> getRequestsOthers(long userId) {
        return get("/all", userId);
    }

    public ResponseEntity<Object> getRequestById(long id) {
        return get("/" + id);
    }
}

