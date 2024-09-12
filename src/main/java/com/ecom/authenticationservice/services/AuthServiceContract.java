package com.ecom.authenticationservice.services;

import com.ecom.authenticationservice.dtos.SignUpRequestDto;
import com.ecom.authenticationservice.dtos.UserDto;

public interface AuthServiceContract {
    public UserDto signUp(SignUpRequestDto signUpRequestDto);
}
