package com.microservices.user.infrastructure.exceptions;

import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ProblemDetail> handleUnauthorizedInSecurity(BaseException ex){
        ProblemDetail pb = ex.problemDetail();
        return ResponseEntity.status(pb.getStatus()).body(pb);
    }


}
