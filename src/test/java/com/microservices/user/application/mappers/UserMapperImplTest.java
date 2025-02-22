package com.microservices.user.application.mappers;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.domain.model.User;
import com.microservices.user.infrastructure.persistence.UserEntity;
import com.microservices.user.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperImplTest {

    @Autowired
    private UserMapper userMapper;

    private User user;
    private UserDto userDto;
    private UserEntity userEntity;

    @BeforeEach
    void setup() {
        this.user = UserTestFactory.createUser();
        this.userDto = UserTestFactory.createUserDto();
        this.userEntity = UserTestFactory.createUserEntity();
    }

    @Test
    void userToDtoTest() {

        UserDto result = userMapper.toDto(user);

        assertNotNull(result);
        assertThat(result.nomeCompleto()).isEqualTo(user.getNomeCompleto());
        assertThat(result.email()).isEqualTo(user.getEmail());

        UserDto nullResult = userMapper.toDto(null);
        assertNull(nullResult);
    }

    @Test
    void dtoToUserTest() {

        User result = userMapper.toUser(userDto);

        assertNotNull(result);
        assertThat(result.getEmail()).isEqualTo(userDto.email());
        assertThat(result.getNomeCompleto()).isEqualTo(userDto.nomeCompleto());

        User nullResult = userMapper.toUser(null);
        assertNull(nullResult);
    }

    @Test
    void toUserWithId() {
        String id = "1";

        User result = userMapper.toUserWithId(id, userDto);

        assertNotNull(result);
        assertThat(result.getEmail()).isEqualTo(userDto.email());
        assertThat(result.getNomeCompleto()).isEqualTo(userDto.nomeCompleto());


    }

    @Test
    void idAndDtoParameterNullInToUserWithIdTest() {

        User nullResult = userMapper.toUserWithId(null, null);
        assertNull(nullResult);
    }

    @Test
    void onlyIdParameterNullInToUserWithIdTest() {

        User idNullResult = userMapper.toUserWithId(null, userDto);
        assertNotNull(idNullResult);
        assertEquals(idNullResult.getEmail(), userDto.email());
        assertEquals(idNullResult.getNomeCompleto(), userDto.nomeCompleto());

    }

    @Test
    void onlyDtoParameterNullInToUserWithIdTest() {
        String id = "1";

        User dtoNullResult = userMapper.toUserWithId(id, null);
        assertNotNull(dtoNullResult);
        assertEquals(id, dtoNullResult.getId());
    }

    @Test
    void listUserToListDtoTest(){

        List<UserDto> result = userMapper.toListDto(List.of(user));

        assertThat(result).isNotEmpty().hasSize(1);
        assertThat(result.getFirst().email()).isEqualTo(user.getEmail());
        assertThat(result.getFirst().nomeCompleto()).isEqualTo(user.getNomeCompleto());


        List<UserDto> nullResult = userMapper.toListDto(null);
        assertNull(nullResult);
    }


    @Test
    void userToPersist(){

        UserEntity result = userMapper.toPersist(user);

        assertNotNull(result);
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getNomeCompleto()).isEqualTo(user.getNomeCompleto());

        UserEntity nullResult = userMapper.toPersist(null);
        assertNull(nullResult);

    }

    @Test
    void persistToDomain(){

        User result = userMapper.toDomain(userEntity);

        assertNotNull(result);
        assertThat(result.getEmail()).isEqualTo(userEntity.getEmail());
        assertThat(result.getNomeCompleto()).isEqualTo(userEntity.getNomeCompleto());

        User nullResult = userMapper.toDomain(null);
        assertNull(nullResult);

    }

    @Test
    void listEntityToListDomainTest(){

        List<User> result = userMapper.listEntityToListDomain(List.of(userEntity));

        assertThat(result).isNotEmpty().hasSize(1);
        assertThat(result.getFirst().getEmail()).isEqualTo(userEntity.getEmail());
        assertThat(result.getFirst().getNomeCompleto()).isEqualTo(userEntity.getNomeCompleto());

        List<User> nullResult = userMapper.listEntityToListDomain(null);
        assertNull(nullResult);
    }

}