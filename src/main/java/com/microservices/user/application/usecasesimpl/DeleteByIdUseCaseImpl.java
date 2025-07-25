package com.microservices.user.application.usecasesimpl;

import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.domain.ports.inbound.DeleteByIdUseCase;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteByIdUseCaseImpl implements DeleteByIdUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void deleteUser(String id) {
        userRepositoryPort.findById(id).orElseThrow(() ->
                new UserNotFoundException("User's email not found!"));
        userRepositoryPort.deleteUser(id);
    }

}
