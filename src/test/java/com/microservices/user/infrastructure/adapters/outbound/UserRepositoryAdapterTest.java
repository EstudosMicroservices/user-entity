package com.microservices.user.infrastructure.adapters.outbound;

import com.microservices.user.application.mappers.UserMapper;
import com.microservices.user.domain.model.User;
import com.microservices.user.infrastructure.persistence.UserEntity;
import com.microservices.user.infrastructure.persistence.UserRepository;
import com.microservices.user.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserRepositoryAdapterTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserRepositoryAdapter userRepositoryAdapter;

    private UserEntity userEntity;
    private User user;


    @BeforeEach
    void setup(){
        this.userEntity = UserTestFactory.createUserEntity();
        this.user = UserTestFactory.createUser();
    }

    static Stream<TestFunction> provideTestMethods(){
        return Stream.of(
                new TestFunction("createUser",
                        adapter -> adapter.createUser(User.builder().build())),
                new TestFunction("updateUser",
                        adapter -> adapter.updateUser(User.builder().build()))
        );
    }

    @Test
    void findUserByEmailTest(){

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(userEntity));
        when(userMapper.toDomain(userEntity)).thenReturn(user);

        Optional<User> result = userRepositoryAdapter.findUserByEmail(user.getEmail());

        assertTrue(result.isPresent());
        assertThat(result.get().getEmail()).isEqualTo(user.getEmail());
        verify(userRepository, times(1)).findByEmail(result.get().getEmail());
        verify(userMapper, times(1)).toDomain(any(UserEntity.class));
    }

    @Test
    void findUserByIdTest(){
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(userEntity));
        when(userMapper.toDomain(userEntity)).thenReturn(user);

        Optional<User> result = userRepositoryAdapter.findById(user.getId());

        assertTrue(result.isPresent());
        assertThat(result.get().getId()).isEqualTo(user.getId());
        verify(userRepository, times(1)).findById("1");
    }

    @Test
    void listUsersTest(){

        UserEntity userEntity2 = UserTestFactory.createUserEntity();
        userEntity2.setId("2");

        User user2 = UserTestFactory.createUser();
        user2.setId("2");

        List<UserEntity> userEntityList = List.of(userEntity,userEntity2);

        when(userRepository.findAll()).thenReturn(userEntityList);
        when(userMapper.listEntityToListDomain(userEntityList)).thenReturn(List.of(user, user2));

        List<User> result = userRepositoryAdapter.listUsers();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo("1");
        assertThat(result.get(1).getId()).isEqualTo("2");

        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).listEntityToListDomain(userEntityList);
    }


//  Just testing this way
//  I don't think it's worth it, though.

    @ParameterizedTest
    @MethodSource("provideTestMethods")
    void createAndUpdateUserTest(TestFunction testFunction) {

        when(userMapper.toPersist(any(User.class))).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDomain(any(UserEntity.class))).thenReturn(user);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        testFunction.apply(userRepositoryAdapter);

        verify(userMapper, times(1)).toPersist(userCaptor.capture());
        verify(userRepository, times(1)).save(userEntity);
        verify(userMapper, times(1)).toDomain(userEntity);
        assertThat(userEntity.getId()).isEqualTo(user.getId());
        assertThat(userEntity.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void deleteUserTest(){

        doNothing().when(userRepository).deleteById(user.getId());

        userRepositoryAdapter.deleteUser(user.getId());

        verify(userRepository, times(1)).deleteById(user.getId());
    }


    static class TestFunction {
        private final String methodName;
        private final Consumer<UserRepositoryAdapter> function;

        public TestFunction(String methodName, Consumer<UserRepositoryAdapter> function) {
            this.methodName = methodName;
            this.function = function;
        }

        public void apply(UserRepositoryAdapter adapter) {
            function.accept(adapter);
        }

        @Override
        public String toString() {
            return methodName;
        }
    }


}
