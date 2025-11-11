package com.microservices.user.infrastructure.adapters.inbound;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.mappers.UserMapper;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.inbound.user.query.FindAllUsersPort;
import com.microservices.user.domain.ports.inbound.user.query.FindUserByEmailPort;
import com.microservices.user.domain.ports.inbound.user.query.FindUserByIdPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user-profile")
public class UserController {

    private final FindAllUsersPort findAllUsersPort;
    private final FindUserByIdPort findUserByIdPort;
    private final FindUserByEmailPort findUserByEmailPort;
    private final UserMapper userMapper;

    @GetMapping("findAll")
    public ResponseEntity<List<UserDto>> findAll() {
        List<User> userList = findAllUsersPort.findAll();
        return ResponseEntity.ok().body(userMapper.toListDto(userList));
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<UserDto> findById(@Valid @PathVariable String id) {
        User user = findUserByIdPort.findById(id);
        return ResponseEntity.ok().body(userMapper.toDto(user));
    }

    @GetMapping("findByEmail/{email}")
    public ResponseEntity<UserDto> findByEmail(@Valid @PathVariable String email) {
        User response = findUserByEmailPort.findByEmail(email);
        return ResponseEntity.ok().body(userMapper.toDto(response));
    }

}
