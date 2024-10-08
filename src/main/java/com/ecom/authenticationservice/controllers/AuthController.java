package com.ecom.authenticationservice.controllers;

import com.ecom.authenticationservice.dtos.*;
import com.ecom.authenticationservice.exceptions.InvalidTokenException;
import com.ecom.authenticationservice.models.SessionStatus;
import com.ecom.authenticationservice.services.AuthService;
import com.ecom.authenticationservice.services.AuthServiceContract;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceContract authServiceDI; //implementation is set during runtime

    public AuthController(AuthServiceContract authServiceDI) {
        this.authServiceDI = authServiceDI; //Checks Implementation of Interface & Injects, @Primary service gets injected if multiple implementaions are found
    }

    @PostMapping("login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return authServiceDI.login(loginRequestDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto){
        return null;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        UserDto userDto = authServiceDI.signUp(signUpRequestDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateUser(@RequestBody ValidateTokenRequestDto validateTokenRequestDto) throws InvalidTokenException {
        SessionStatus sessionStatus = authServiceDI.validateToken(validateTokenRequestDto);
        return new ResponseEntity<>(sessionStatus, HttpStatus.OK);
    }
}
