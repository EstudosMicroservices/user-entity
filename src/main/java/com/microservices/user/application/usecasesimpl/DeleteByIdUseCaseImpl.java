package com.microservices.user.application.usecasesimpl;

import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.application.usecases.DeleteByIdUseCase;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteByIdUseCaseImpl implements DeleteByIdUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void deleteUser(String id) {
        User existingUser = userRepositoryPort.findById(id).orElseThrow(() ->
                new UserNotFoundException("User's email not found!"));
        userRepositoryPort.deleteUser(existingUser.getId());
    }

}
