package com.microservices.user.application.services.usecasesimpl;

import com.microservices.user.application.usecases.DeleteByEmailUseCase;
import com.microservices.user.domain.model.User;
import com.microservices.user.infrastructure.exceptions.UserNotFoundException;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteByEmailUseCaseImpl implements DeleteByEmailUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void deleteUser(String email) {
//  Checar e implementar
        User existingUser = userRepositoryPort.findUserByEmail(email).orElseThrow(() ->
                new UserNotFoundException("User's email not found."));
        userRepositoryPort.deleteUser(existingUser.getId());
    }

}
