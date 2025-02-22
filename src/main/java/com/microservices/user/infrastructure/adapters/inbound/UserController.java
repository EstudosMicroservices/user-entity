package com.microservices.user.infrastructure.adapters.inbound;

import com.microservices.user.application.services.dto.UserDto;
import com.microservices.user.domain.services.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("User")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping("findAll")
    public ResponseEntity<List<UserDto>> findAll (){
        return ResponseEntity.ok().body(userServiceImpl.findAll());
    }

    @GetMapping("findByEmail")
    public ResponseEntity<UserDto> findByEmail(@Valid @RequestParam String email){
        return ResponseEntity.ok().body(userServiceImpl.findByEmail(email));
    }

    @PostMapping("create")
    public ResponseEntity<UserDto> create (@RequestBody @Valid UserDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userServiceImpl.createUser(userDto));
    }

    @PutMapping("update")
    public ResponseEntity<UserDto> update(@Valid @RequestParam String id, @Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok().body(userServiceImpl.updateuser(id, userDto));
    }

    @DeleteMapping("delete")
    public ResponseEntity<Void> delete (@Valid @RequestParam String id){
        userServiceImpl.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
