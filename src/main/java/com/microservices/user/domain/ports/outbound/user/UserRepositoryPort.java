package com.microservices.user.domain.ports.outbound.user;

import com.microservices.user.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {

    Optional<User> findUserByEmail(String email);

    Optional<User> findById(String id);

    List<User> listUsers();

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(String id);

    void deleteAll();
}
