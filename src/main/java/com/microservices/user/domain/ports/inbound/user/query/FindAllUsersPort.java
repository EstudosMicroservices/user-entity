package com.microservices.user.domain.ports.inbound.user.query;

import com.microservices.user.domain.model.User;

import java.util.List;

public interface FindAllUsersPort {
    List<User> findAll();
}
