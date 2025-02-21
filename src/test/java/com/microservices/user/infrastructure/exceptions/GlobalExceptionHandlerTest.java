package com.microservices.user.infrastructure.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RestController
class MockController {
    @GetMapping("/testException")
    public String testException() {
        throw new BaseException(HttpStatus.BAD_REQUEST, "Bad Request", "Test exception");
    }
}

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MockController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testHandleBaseException() throws Exception {
        mockMvc.perform(get("/testException"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":400,\"title\":\"Bad Request\",\"detail\":\"Test exception\"}")); // Expected error response
    }
}
