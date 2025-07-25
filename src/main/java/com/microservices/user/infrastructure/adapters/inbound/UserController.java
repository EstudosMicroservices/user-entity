package com.microservices.user.infrastructure.adapters.inbound;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.domain.ports.inbound.*;
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

    private final FindAllUseCase findAllUseCase;
    private final FindByIdUseCase findByIdUseCase;
    private final FindByEmailUseCase findByEmailUseCase;
    private final CreateUseCase createUseCase;
    private final UpdateUseCase updateUseCase;
    private final DeleteByIdUseCase deleteByIdUseCase;

    @GetMapping("findAll")
    public ResponseEntity<List<UserDto>> findAll (){
        return ResponseEntity.ok().body(findAllUseCase.findAll());
    }

    @GetMapping("findBy/id/{id}")
    public ResponseEntity<UserDto> findById(@Valid @PathVariable String id){
        return ResponseEntity.ok().body(findByIdUseCase.findById(id));
    }

    @GetMapping("findBy/email/{email}")
    public ResponseEntity<UserDto> findByEmail(@Valid @PathVariable String email){
        return ResponseEntity.ok().body(findByEmailUseCase.findByEmail(email));
    }

    @PostMapping("create")
    public ResponseEntity<UserDto> create (@RequestBody @Valid UserDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(createUseCase.createUser(userDto));
    }

    @PutMapping("update")
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok().body(updateUseCase.updateUser(userDto));
    }

    @DeleteMapping("delete")
    public ResponseEntity<Void> delete (@Valid @RequestParam String id){
        deleteByIdUseCase.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
