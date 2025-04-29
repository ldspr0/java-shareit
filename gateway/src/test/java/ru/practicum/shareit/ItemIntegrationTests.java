package ru.practicum.shareit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.ItemClient;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.comments.dto.NewCommentRequest;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemClient itemClient;

    @Test
    public void createItem_ValidRequest_ReturnsCreated() throws Exception {
        NewItemRequest request = new NewItemRequest();
        request.setName("Item");
        request.setDescription("Description");
        request.setAvailable(true);

        ItemDto responseDto = new ItemDto();
        responseDto.setId(1L);

        when(itemClient.createItem(anyLong(), any(NewItemRequest.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(responseDto));

        mockMvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void updateItem_ValidRequest_ReturnsOk() throws Exception {
        UpdateItemRequest request = new UpdateItemRequest();
        request.setName("Updated Name");

        ItemDto responseDto = new ItemDto();
        responseDto.setId(1L);
        responseDto.setName("Updated Name");

        when(itemClient.updateItem(anyLong(), anyLong(), any(UpdateItemRequest.class)))
                .thenReturn(ResponseEntity.ok(responseDto));

        mockMvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    public void getItem_ExistingId_ReturnsOk() throws Exception {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);

        when(itemClient.getItem(anyLong(), anyLong()))
                .thenReturn(ResponseEntity.ok(itemDto));

        mockMvc.perform(get("/items/1")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void getAllItems_ValidRequest_ReturnsOk() throws Exception {
        when(itemClient.getAllItems(anyLong()))
                .thenReturn(ResponseEntity.ok(Collections.emptyList()));

        mockMvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void search_ValidRequest_ReturnsOk() throws Exception {
        mockMvc.perform(get("/items/search")
                        .param("text", "test"))
                .andExpect(status().isOk());
    }

    @Test
    public void addComment_ValidRequest_ReturnsOk() throws Exception {
        NewCommentRequest request = new NewCommentRequest();
        request.setText("Comment text");

        mockMvc.perform(post("/items/1/comment")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}