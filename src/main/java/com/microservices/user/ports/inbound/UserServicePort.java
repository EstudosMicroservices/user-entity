package com.microservices.user.ports.inbound;

import com.microservices.user.domain.user.UserDto;

import java.util.List;

public interface UserServicePort{

    List<UserDto> findAll();
    UserDto findByEmail(String email);
    UserDto createUser(UserDto userDto);
    UserDto updateuser(UserDto userDto);
    boolean deleteByEmail(String email);
}
