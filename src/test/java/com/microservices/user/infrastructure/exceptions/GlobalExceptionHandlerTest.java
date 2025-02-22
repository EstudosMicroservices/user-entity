package com.microservices.user.infrastructure.exceptions;

import com.microservices.user.application.exceptions.BaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MockController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleBaseException() throws Exception {
        mockMvc.perform(get("/testException"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":400,\"title\":\"Bad Request\",\"detail\":\"Test exception\"}")); // Expected error response
    }


    @Test
    void testMapToProblemDetail_validHttpStatus() {
        // Valid HTTP status code "200" corresponding to HttpStatus.OK
        BaseException validException = new BaseException("200", "Success", "Request was successful.");

        // Act
        ProblemDetail problemDetail = globalExceptionHandler.mapToProblemDetail(validException);

        // Assert
        assertEquals(HttpStatus.OK.value(), problemDetail.getStatus());
        assertEquals("Success", problemDetail.getTitle());
        assertEquals("Request was successful.", problemDetail.getDetail());
    }

    @Test
    void testMapToProblemDetail_invalidHttpStatus() {
        // Invalid HTTP status code "999"
        BaseException invalidException = new BaseException("999", "Unknown Status", "This status code is invalid.");

        // Act & Assert: Assert that the AssertionError is thrown
        assertThrows(AssertionError.class, () -> {
            globalExceptionHandler.mapToProblemDetail(invalidException);
        });
    }

}

@RestController
class MockController {
    @GetMapping("/testException")
    public String testException() {
        throw new BaseException("400", "Bad Request", "Test exception");
    }
}
