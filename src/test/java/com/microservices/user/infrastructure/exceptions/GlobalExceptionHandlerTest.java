package com.microservices.user.infrastructure.exceptions;

import com.microservices.user.application.exceptions.BaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MockController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Deve capturar uma BaseException e retornar um ProblemDetail formatado")
    void testHandleBaseException() throws Exception {
        mockMvc.perform(get("/test-exception"))
                .andExpect(status().isConflict())
                .andExpect(content().json("{\"status\":409,\"title\":\"User already exists!\",\"detail\":\"Test exception detail\"}"));
    }

}

@RestController
class MockController {

    @GetMapping("/test-exception")
    public void throwTestException() {
        throw new TestBaseException(HttpStatus.CONFLICT, "User already exists!", "Test exception detail");
    }

    private static class TestBaseException extends BaseException {
        public TestBaseException(HttpStatusCode httpStatusCode, String title, String detail) {
            super(httpStatusCode, title, detail);
        }
    }
}
