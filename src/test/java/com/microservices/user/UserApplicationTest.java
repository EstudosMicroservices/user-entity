package com.microservices.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserApplicationTest {

    @Test
    void contextLoads() {
        UserApplication.main(new String[]{});
    }
}