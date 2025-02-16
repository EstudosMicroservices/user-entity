package com.microservices.user.infrastructure.adapters.outbound;

import com.microservices.user.domain.user.User;
import com.microservices.user.infrastructure.persistence.UserRepository;
import com.microservices.user.ports.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> listUsers(){
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user){
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

}
