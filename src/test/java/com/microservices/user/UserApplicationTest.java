package com.microservices.user;

import com.microservices.user.infrastructure.database.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserApplicationTest extends AbstractIntegrationTest {

    @Test
    void contextLoads() {
    /*
    * Smoke Test
    */

    }
}