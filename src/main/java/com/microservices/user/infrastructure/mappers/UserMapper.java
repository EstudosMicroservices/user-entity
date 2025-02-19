package com.microservices.user.infrastructure.mappers;

import com.microservices.user.domain.dto.UserDto;
import com.microservices.user.domain.model.User;
import com.microservices.user.infrastructure.exceptions.mapping.DateMappingException;
import com.microservices.user.infrastructure.persistence.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataNascimento", source = "dataNascimento", qualifiedByName = "localDateToString")
    User toUser(UserDto userDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userDto.dataNascimento", target = "dataNascimento", qualifiedByName = "localDateToString")
    User toUserWithId(String id, UserDto userDto);

    @Mapping(source = "dataNascimento", target = "dataNascimento", qualifiedByName = "stringToLocalDate")
    UserDto toDto(User user);

    UserEntity toPersist(User user);

    User toDomain(UserEntity userEntity);

    List<User> listEntityToListDomain(List<UserEntity> userList);

    @Mapping(source = "dataNascimento", target = "dataNascimento", qualifiedByName = "stringListToLocalDateList")
    List<UserDto> toListDto(List<User> user);

    @Named("localDateToString")
    static String localDateToString(LocalDate date) {
        if (date == null) {
            throw new DateMappingException("LocalDate is empty or null.");
        }
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    @Named("stringToLocalDate")
    static LocalDate stringToLocalDate(String date) {
        if (date == null || date.isEmpty()) {
            throw new DateMappingException("String of date is empty or null.");
        }
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid format to date: " + date);
        }
    }

    @Named("stringListToLocalDateList")
    static List<LocalDate> stringListToLocalDateList(List<String> dates) {
        if (dates == null || dates.isEmpty()) {
            throw new DateMappingException("List of date is empty or null.");
        }
        return dates.stream()
                .map(UserMapper::stringToLocalDate)
                .toList();
    }
}
