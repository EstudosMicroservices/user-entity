package com.microservices.user.application.implementations.user.query;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.application.mappers.UserMapper;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.inbound.user.query.FindAllUsersPort;
import com.microservices.user.domain.ports.outbound.user.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindAllUsersImpl implements FindAllUsersPort {

    private final UserRepositoryPort userRepositoryPort;
    private final UserMapper userMapper;
    private final Logger log = LoggerFactory.getLogger(FindAllUsersImpl.class);

    @Override
    public List<UserDto> findAll() {
        log.info("[FindAllUsersImpl] Finding all users.");
        List<User> userList = userRepositoryPort.listUsers();
        if (userList.isEmpty()) {
            log.error("[FindAllUsersImpl] User list is empty!");
            throw new UserNotFoundException("User's list is empty!");
        }
        log.info("[FindAllUsersImpl] Successfully retrieved User's list!");
        return userMapper.toListDto(userList);
    }
}
