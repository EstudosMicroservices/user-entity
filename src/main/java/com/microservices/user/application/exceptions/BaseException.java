package com.microservices.user.application.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;


@RequiredArgsConstructor
@Getter
public class BaseException extends RuntimeException {

    private final HttpStatusCode httpStatusCode;
    private final String title;
    private final String detail;

}