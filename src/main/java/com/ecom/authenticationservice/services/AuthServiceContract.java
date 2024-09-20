package com.ecom.authenticationservice.services;

import com.ecom.authenticationservice.dtos.LoginRequestDto;
import com.ecom.authenticationservice.dtos.SignUpRequestDto;
import com.ecom.authenticationservice.dtos.UserDto;
import org.springframework.http.ResponseEntity;

public interface AuthServiceContract {
    public UserDto signUp(SignUpRequestDto signUpRequestDto);
    public ResponseEntity<UserDto> login(LoginRequestDto loginRequestDto);
}
