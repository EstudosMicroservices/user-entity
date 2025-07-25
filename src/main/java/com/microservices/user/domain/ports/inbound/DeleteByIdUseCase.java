package com.microservices.user.domain.ports.inbound;


public interface DeleteByIdUseCase {
    void deleteUser(String id);
}
