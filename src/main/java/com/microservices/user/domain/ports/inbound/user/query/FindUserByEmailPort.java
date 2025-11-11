package com.microservices.user.domain.ports.inbound.user.query;

import com.microservices.user.domain.model.User;

public interface FindUserByEmailPort {
    User findByEmail(String email);
}
