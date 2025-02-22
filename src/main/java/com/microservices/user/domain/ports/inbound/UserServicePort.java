package com.microservices.user.domain.ports.inbound;

import com.microservices.user.application.services.dto.UserDto;

import java.util.List;

public interface UserServicePort{

    List<UserDto> findAll();
    UserDto findByEmail(String email);
    UserDto createUser(UserDto userDto);
    UserDto updateuser(String id, UserDto userDto);
    void deleteUser(String id);
}
