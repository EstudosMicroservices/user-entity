package com.microservices.user.infrastructure.mappers;

import com.microservices.user.domain.user.User;
import com.microservices.user.domain.user.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser (UserDto userDto);
    UserDto toDto (User user);

    List<User> toListUser(List<UserDto> userDto);
    List<UserDto> toListDto(List<User> user);

}
