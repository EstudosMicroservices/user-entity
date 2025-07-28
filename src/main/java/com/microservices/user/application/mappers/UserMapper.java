package com.microservices.user.application.mappers;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.domain.model.User;
import com.microservices.user.infrastructure.persistence.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toUser(UserDto userDto);

    UserDto toDto(User user);

    List<UserDto> toListDto(List<User> user);

    UserEntity toPersist(User user);

    User toDomain(UserEntity userEntity);

    List<User> listEntityToListDomain(List<UserEntity> userList);


    @Mapping(target = "id", ignore = true)
    void updateUserEntity(UserDto userDto, @MappingTarget User existingUser);

}
