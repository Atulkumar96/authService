package com.ecom.authenticationservice.dtos;

import lombok.Getter;

@Getter
public class ValidateTokenRequestDto {
    private String token;
    private Long userId;
}
