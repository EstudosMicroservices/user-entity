package com.microservices.user.infrastructure.exceptions;

import com.microservices.user.application.exceptions.BaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
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
    @DisplayName("Handle BaseException: should return the correct status and body for a custom exception.")
    void testHandleBaseException() throws Exception {
        mockMvc.perform(get("/test-base-exception"))
                .andExpect(status().isConflict())
                .andExpect(content().json("{\"status\":409,\"title\":\"User already exists!\",\"detail\":\"Test exception detail\"}"));
    }

    @Test
    @DisplayName("Handle IllegalArgumentException: should return a 400 Bad Request with a clear message.")
    void testHandleIllegalArgumentException() throws Exception {
        mockMvc.perform(get("/test-illegal-argument"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":400,\"title\":\"Invalid argument\",\"detail\":\"Test illegal argument message\"}"));
    }

    @Test
    @DisplayName("Handle DataIntegrityViolationException: should return a 409 Conflict for data conflicts.")
    void testHandleDataIntegrityViolationException() throws Exception {
        mockMvc.perform(get("/test-data-integrity-violation"))
                .andExpect(status().isConflict())
                .andExpect(content().json("{\"status\":409,\"title\":\"Data integrity violation\",\"detail\":\"Test data integrity violation message\"}"));
    }
}

/**
 * A simple mock controller to trigger specific exceptions for testing.
 */
@RestController
class MockController {

    @GetMapping("/test-base-exception")
    public void throwBaseException() {
        throw new TestBaseException(HttpStatus.CONFLICT, "User already exists!", "Test exception detail");
    }

    @GetMapping("/test-illegal-argument")
    public void throwIllegalArgumentException() {
        throw new IllegalArgumentException("Test illegal argument message");
    }

    @GetMapping("/test-data-integrity-violation")
    public void throwDataIntegrityViolationException() {
        throw new DataIntegrityViolationException("Test data integrity violation message");
    }

    private static class TestBaseException extends BaseException {
        public TestBaseException(HttpStatusCode httpStatusCode, String title, String detail) {
            super(httpStatusCode, title, detail);
        }
    }
}
