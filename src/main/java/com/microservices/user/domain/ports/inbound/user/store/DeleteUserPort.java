package com.microservices.user.domain.ports.inbound.user.store;

public interface DeleteUserPort {

    void deleteUser(String id);
}
