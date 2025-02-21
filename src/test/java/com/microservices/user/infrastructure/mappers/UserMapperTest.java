package com.microservices.user.infrastructure.mappers;

import com.microservices.user.infrastructure.exceptions.mapping.DateMappingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserMapperTest {

    private LocalDate localDate;
    private String date;

    @BeforeEach
    void setup(){
        this.localDate = LocalDate.of(2000, 5, 30);
        this.date = "30-05-2000";
    }

    @Test
    void localDateToStringTest(){
        localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String result = UserMapper.localDateToString(localDate);

        assertNotNull(result);
        assertThat(result.charAt(0)).isEqualTo('3');
        assertThat(result.charAt(9)).isEqualTo('0');
        assertThat(result).hasSize(10);
    }

    @Test
    void dateNullLocalDateToStringTest(){

        DateMappingException exception = assertThrows(DateMappingException.class,() ->
                UserMapper.localDateToString(null));

        assertNotNull(exception);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getHttpStatus());
        assertEquals("Can't mapping the date!", exception.getTitle());
        assertEquals("LocalDate is null!", exception.getDetail());

    }

    @Test
    void stringToLocalDateTest(){
        LocalDate result = UserMapper.stringToLocalDate(date);

        assertNotNull(result);
        assertThat(result.getYear()).isEqualTo(2000);
        assertThat(result.getMonthValue()).isEqualTo(5);
        assertThat(result.getDayOfMonth()).isEqualTo(30);

    }

    @Test
    void dateNullStringToLocalDateTest(){
        DateMappingException exception = assertThrows(DateMappingException.class,() ->
                UserMapper.stringToLocalDate(null));

        assertNotNull(exception);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getHttpStatus());
        assertEquals("Can't mapping the date!", exception.getTitle());
        assertEquals("String of date is empty or null!", exception.getDetail());

    }

    @Test
    void dateEmptyStringToLocalDateTest(){

        String emptyDate = "";

        DateMappingException exception = assertThrows(DateMappingException.class,() ->
                UserMapper.stringToLocalDate(emptyDate));

        assertNotNull(exception);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getHttpStatus());
        assertEquals("Can't mapping the date!", exception.getTitle());
        assertEquals("String of date is empty or null!", exception.getDetail());
    }

    @Test
    void stringListToLocalDateListTest(){

        List<LocalDate> result = UserMapper.stringListToLocalDateList(List.of(date));

        assertNotNull(result);
        assertThat(result.getFirst().getYear()).isEqualTo(2000);
        assertThat(result.getFirst().getMonthValue()).isEqualTo(5);
        assertThat(result.getFirst().getDayOfMonth()).isEqualTo(30);
    }

    @Test
    void dateNullStringListToLocalDateListTest(){

        DateMappingException exception = assertThrows(DateMappingException.class, () ->
                UserMapper.stringListToLocalDateList(null));

        assertNotNull(exception);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getHttpStatus());
        assertEquals("Can't mapping the date!", exception.getTitle());
        assertEquals("List of date is empty or null!", exception.getDetail());
    }

    @Test
    void dateEmptyStringListToLocalDateListTest(){

        List<String> emptyList = new ArrayList<>();
        DateMappingException exception = assertThrows(DateMappingException.class, () ->
                UserMapper.stringListToLocalDateList(emptyList));

        assertNotNull(exception);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getHttpStatus());
        assertEquals("Can't mapping the date!", exception.getTitle());
        assertEquals("List of date is empty or null!", exception.getDetail());
    }
}
