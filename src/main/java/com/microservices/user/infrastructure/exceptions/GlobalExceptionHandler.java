package com.microservices.user.infrastructure.exceptions;

import com.microservices.user.application.exceptions.BaseException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ProblemDetail> handleBaseException(BaseException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(ex.getHttpStatusCode());
        problemDetail.setTitle(ex.getTitle());
        problemDetail.setDetail(ex.getDetail());

        return ResponseEntity.status(ex.getHttpStatusCode()).body(problemDetail);
    }
}
