package com.microservices.user.infrastructure.adapters.inbound;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.domain.ports.inbound.user.query.FindAllUsersPort;
import com.microservices.user.domain.ports.inbound.user.query.FindUserByEmailPort;
import com.microservices.user.domain.ports.inbound.user.query.FindUserByIdPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final FindAllUsersPort findAllUsersPort;
    private final FindUserByIdPort findUserByIdPort;
    private final FindUserByEmailPort findUserByEmailPort;

    @GetMapping("findAll")
    public ResponseEntity<List<UserDto>> findAll() {
        log.info("[UserController] GET request received to /user-profile/findAll");
        return ResponseEntity.ok().body(findAllUsersPort.findAll());
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<UserDto> findById(@Valid @PathVariable String id) {
        log.info("[UserController] GET request received to /user-profile/findBy/id");
        return ResponseEntity.ok().body(findUserByIdPort.findById(id));
    }

    @GetMapping("findByEmail/{email}")
    public ResponseEntity<UserDto> findByEmail(@Valid @PathVariable String email) {
        log.info("[UserController] GET request received to /user-profile/findBy/email");
        return ResponseEntity.ok().body(findUserByEmailPort.findByEmail(email));
    }

}
