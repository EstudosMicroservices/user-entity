package com.microservices.user.application.mappers;

import com.microservices.user.application.events.UserRegisteredEvent;
import com.microservices.user.domain.command.CreateUserCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

    CreateUserCommand toDomain(UserRegisteredEvent userRegisteredEvent);
}
