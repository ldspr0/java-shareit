package ru.practicum.shareit.item;

import org.mapstruct.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemDto itemToItemDto(Item item);

    Item newItemRequestToItem(NewItemRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Item updateItemFromRequest(UpdateItemRequest request, @MappingTarget Item item);
}
