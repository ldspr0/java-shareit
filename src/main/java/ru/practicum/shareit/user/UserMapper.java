package ru.practicum.shareit.user;

import org.mapstruct.*;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "name", source = "username")
    UserDto userToUserDto(User user);

    @Mapping(source = "name", target = "username")
    User newUserRequestToUser(NewUserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "name", target = "username")
    User updateUserFromRequest(UpdateUserRequest request, @MappingTarget User user);
}
