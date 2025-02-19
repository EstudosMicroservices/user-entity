package com.microservices.user.infrastructure.config;

import com.microservices.user.application.services.UserServiceImpl;
import com.microservices.user.application.services.usecasesimpl.*;
import com.microservices.user.domain.ports.inbound.UserServicePort;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
import com.microservices.user.infrastructure.adapters.outbound.UserRepositoryAdapter;
import com.microservices.user.infrastructure.mappers.UserMapper;
import com.microservices.user.infrastructure.persistence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public UserServiceImpl userService(
            CreateUseCaseImpl createUseCase,
            FindByEmailUseCaseImpl findByEmailUseCase,
            FindAllUseCaseImpl findAllUseCase,
            DeleteByIdUseCaseImpl deleteByIdUseCase,
            UpdateUseCaseImpl updateUseCase
    ) {
        return new UserServiceImpl(
                createUseCase, findByEmailUseCase, findAllUseCase, deleteByIdUseCase, updateUseCase
        );
    }

    @Bean
    public CreateUseCaseImpl createUseCase(UserRepositoryPort userRepositoryPort, UserMapper userMapper) {
        return new CreateUseCaseImpl(userRepositoryPort, userMapper);
    }

    @Bean
    public FindByEmailUseCaseImpl findByEmailUseCase(UserRepositoryPort userRepositoryPort, UserMapper userMapper){
        return new FindByEmailUseCaseImpl(userRepositoryPort, userMapper);
    }

    @Bean
    public FindAllUseCaseImpl findAllUseCaseImpl(UserRepositoryPort userRepositoryPort, UserMapper userMapper){
        return new FindAllUseCaseImpl(userRepositoryPort, userMapper);
    }

    @Bean
    public DeleteByIdUseCaseImpl deleteByEmailUseCaseImpl(UserRepositoryPort userRepositoryPort){
        return new DeleteByIdUseCaseImpl(userRepositoryPort);
    }

    @Bean
    public UpdateUseCaseImpl updateUseCaseImpl(UserRepositoryPort userRepositoryPort,UserMapper userMapper){
        return new UpdateUseCaseImpl(userRepositoryPort, userMapper);
    }
    @Bean
    public UserRepositoryAdapter userRepositoryAdapter(UserRepository userRepository, UserMapper userMapper){
        return new UserRepositoryAdapter(userRepository, userMapper);
    }


    @Bean
    public UserRepositoryPort userRepositoryPort(UserRepositoryAdapter userRepositoryAdapter){
        return userRepositoryAdapter;
    }

    @Bean
    public UserServicePort userServicePort(UserServiceImpl userServicesImpl){
        return userServicesImpl;
    }
}
