package com.microservices.user.infrastructure.config;

import com.microservices.user.application.services.usecasesimpl.UserServiceImpl;
import com.microservices.user.infrastructure.adapters.outbound.UserRepositoryAdapter;
import com.microservices.user.ports.inbound.UserServicePort;
import com.microservices.user.ports.outbound.UserRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public UserRepositoryPort userRepositoryPort(UserRepositoryAdapter userRepositoryAdapter){
        return userRepositoryAdapter;
    }

    @Bean
    public UserServicePort userServicePort(UserServiceImpl userServicesImpl){
        return userServicesImpl;
    }
}
