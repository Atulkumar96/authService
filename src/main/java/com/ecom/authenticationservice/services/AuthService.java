package com.ecom.authenticationservice.services;

import com.ecom.authenticationservice.dtos.SignUpRequestDto;
import com.ecom.authenticationservice.dtos.UserDto;
import com.ecom.authenticationservice.models.User;
import com.ecom.authenticationservice.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthServiceContract{
    //dependencies objects
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;

    public AuthService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    public UserDto signUp(SignUpRequestDto signUpRequestDto) {
        User newUser = new User();
        newUser.setEmail(signUpRequestDto.getEmail());
        newUser.setPassword(bCryptPasswordEncoder.encode(signUpRequestDto.getPassword()));

        User signedUpUser = userRepository.save(newUser);

        UserDto userDto;
        userDto = UserDto.from(signedUpUser);
        return userDto;
    }

}
