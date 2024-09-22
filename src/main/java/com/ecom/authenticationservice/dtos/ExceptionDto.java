package com.ecom.authenticationservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
@AllArgsConstructor
public class ExceptionDto {
    private String message;
    private HttpStatus httpStatus;
}
