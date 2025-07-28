package com.microservices.user.infrastructure.adapters.outbound;

import com.microservices.user.application.mappers.UserMapper;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
import com.microservices.user.infrastructure.persistence.UserEntity;
import com.microservices.user.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDomain);

    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id).map(userMapper::toDomain);
    }

    @Override
    public List<User> listUsers() {
        List<UserEntity> listEntity = userRepository.findAll();
        return userMapper.listEntityToListDomain(listEntity);
    }

    @Override
    public User createUser(User user) {
        UserEntity createUser = userMapper.toPersist(user);
        return userMapper.toDomain(userRepository.save(createUser));
    }

    @Override
    public User updateUser(User user) {
        UserEntity updateUser = userMapper.toPersist(user);
        return userMapper.toDomain(userRepository.save(updateUser));
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

}
