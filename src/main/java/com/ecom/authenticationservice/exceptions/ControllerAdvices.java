package com.ecom.authenticationservice.exceptions;

import com.ecom.authenticationservice.dtos.ExceptionDto;
import com.ecom.authenticationservice.models.SessionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ExceptionDto> handleInvalidTokenException(InvalidTokenException invalidTokenException) {
        return new ResponseEntity<>(new ExceptionDto(invalidTokenException.getMessage(), HttpStatus.UNAUTHORIZED),
                HttpStatus.UNAUTHORIZED);
    }
}
