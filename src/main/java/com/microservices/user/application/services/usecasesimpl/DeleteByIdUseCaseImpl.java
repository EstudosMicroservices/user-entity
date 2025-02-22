package com.microservices.user.application.services.usecasesimpl;

import com.microservices.user.application.services.usecases.DeleteByIdUseCase;
import com.microservices.user.domain.exceptions.user.UserNotFoundException;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteByIdUseCaseImpl implements DeleteByIdUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void deleteUser(String id) {
//  Checar e implementar
        User existingUser = userRepositoryPort.findById(id).orElseThrow(() ->
                new UserNotFoundException("User's email not found!"));
        userRepositoryPort.deleteUser(existingUser.getId());
    }

}
