package com.microservices.user.domain.model.validation;

import com.microservices.user.domain.exceptions.user.DateValidationException;
import com.microservices.user.domain.validation.ValidateDataNascimentoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ValidateDataNascimentoImplTest {

    private ValidateDataNascimentoImpl validateDataNascimentoImpl;

    @BeforeEach
    void setUp() {
        validateDataNascimentoImpl = new ValidateDataNascimentoImpl();
    }

    @Test
    void isValidTest() {
        LocalDate validDate = LocalDate.of(2020, 3, 20);

        boolean result = validateDataNascimentoImpl.isValid(validDate, null);

        assertThat(result).isTrue();
    }

    @Test
    void isValidNullExceptionTest(){

        DateValidationException exception = assertThrows(DateValidationException.class, () ->
                validateDataNascimentoImpl.isValid(null, null));

        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("Time validation error!", exception.getTitle());
        assertEquals("Date value is null!", exception.getDetail());
    }

    @Test
    void isValidTimeTravellerExceptionTest(){

        LocalDate currentTime = LocalDate.now();
        LocalDate value = LocalDate.of(5000, 2, 3);



        DateValidationException exception = assertThrows(DateValidationException.class,() ->
            validateDataNascimentoImpl.isValid(value, null));

        assertNotNull(exception);
        assertThat(value.getYear()).isGreaterThan(currentTime.getYear());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("Time validation error!", exception.getTitle());
        assertEquals("I don't think you're a time traveller.", exception.getDetail());


    }

}