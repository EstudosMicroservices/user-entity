package com.microservices.user.infrastructure.exceptions;

import com.microservices.user.application.exceptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ProblemDetail> handleBaseException(BaseException ex){
        ProblemDetail pb = mapToProblemDetail(ex);
        return ResponseEntity.status(pb.getStatus()).body(pb);
    }

    ProblemDetail mapToProblemDetail(BaseException ex) {
        HttpStatus httpStatus = HttpStatus.resolve(Integer.parseInt(ex.getHttpStatusCode()));

        assert httpStatus != null;
        ProblemDetail pb = ProblemDetail.forStatus(httpStatus);
        pb.setTitle(ex.getTitle());
        pb.setDetail(ex.getDetail());
        return pb;
    }


}
