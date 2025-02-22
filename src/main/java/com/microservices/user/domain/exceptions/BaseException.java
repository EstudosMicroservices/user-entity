package com.microservices.user.domain.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;


@RequiredArgsConstructor
@Getter
public class BaseException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String title;
    private final String detail;

    public ProblemDetail problemDetail() {
        ProblemDetail pb = ProblemDetail.forStatus(httpStatus);
        pb.setTitle(title);
        pb.setDetail(detail);
        return pb;
    }

}