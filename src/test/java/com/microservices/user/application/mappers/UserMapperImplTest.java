package com.microservices.user.application.mappers;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.domain.model.User;
import com.microservices.user.infrastructure.persistence.entity.UserEntity;
import com.microservices.user.utils.UserTestFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        UserMapperImpl.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserMapperImplTest {

    private final UserMapper userMapper;

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
    void listUserToListDtoTest() {

        List<UserDto> result = userMapper.toListDto(List.of(user));

        assertThat(result).isNotEmpty().hasSize(1);
        assertThat(result.getFirst().email()).isEqualTo(user.getEmail());
        assertThat(result.getFirst().nomeCompleto()).isEqualTo(user.getNomeCompleto());


        List<UserDto> nullResult = userMapper.toListDto(null);
        assertNull(nullResult);
    }


    @Test
    void userToPersist() {

        UserEntity result = userMapper.toPersist(user);

        assertNotNull(result);
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getNomeCompleto()).isEqualTo(user.getNomeCompleto());

        UserEntity nullResult = userMapper.toPersist(null);
        assertNull(nullResult);

    }

    @Test
    void persistToDomain() {

        User result = userMapper.toDomain(userEntity);

        assertNotNull(result);
        assertThat(result.getEmail()).isEqualTo(userEntity.getEmail());
        assertThat(result.getNomeCompleto()).isEqualTo(userEntity.getNomeCompleto());

    }

    @Test
    void listEntityToListDomainTest() {

        List<User> result = userMapper.listEntityToListDomain(List.of(userEntity));

        assertThat(result).isNotEmpty().hasSize(1);
        assertThat(result.getFirst().getEmail()).isEqualTo(userEntity.getEmail());
        assertThat(result.getFirst().getNomeCompleto()).isEqualTo(userEntity.getNomeCompleto());

        List<User> nullResult = userMapper.listEntityToListDomain(null);
        assertNull(nullResult);
    }

    @Test
    void updateUserEntityTest(){
        User existingUser = this.user;
        UserDto updatedUser = UserTestFactory.createUpdatedDto(null);

        userMapper.updateUserEntity(updatedUser, existingUser);
        assertEquals(updatedUser.nomeCompleto(), existingUser.getNomeCompleto());
        assertNotEquals(updatedUser.id(), existingUser.getId());

        userMapper.updateUserEntity(null, existingUser);

    }
}