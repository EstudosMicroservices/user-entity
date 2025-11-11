package com.microservices.user.domain.ports.inbound.user.query;

import com.microservices.user.domain.model.User;

public interface FindUserByIdPort {
    User findById(String id);
}
