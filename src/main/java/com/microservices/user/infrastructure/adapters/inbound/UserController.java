package com.microservices.user.infrastructure.adapters.inbound;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.domain.ports.inbound.user.query.FindAllUsersPort;
import com.microservices.user.domain.ports.inbound.user.query.FindUserByEmailPort;
import com.microservices.user.domain.ports.inbound.user.query.FindUserByIdPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user-profile")
public class UserController {

    private final FindAllUsersPort findAllUsersPort;
    private final FindUserByIdPort findUserByIdPort;
    private final FindUserByEmailPort findUserByEmailPort;

    @GetMapping("findAll")
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok().body(findAllUsersPort.findAll());
    }

    @GetMapping("findById")
    public ResponseEntity<UserDto> findById(@Valid @RequestParam String id) {
        return ResponseEntity.ok().body(findUserByIdPort.findById(id));
    }

    @GetMapping("findByEmail")
    public ResponseEntity<UserDto> findByEmail(@Valid @RequestParam String email) {
        return ResponseEntity.ok().body(findUserByEmailPort.findByEmail(email));
    }

}
