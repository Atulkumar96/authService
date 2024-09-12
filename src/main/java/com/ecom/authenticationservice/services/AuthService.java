package com.ecom.authenticationservice.services;

import com.ecom.authenticationservice.dtos.UserDto;
import com.ecom.authenticationservice.models.User;
import com.ecom.authenticationservice.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    //dependencies objects
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;

    public AuthService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    public UserDto signUp(String email, String password) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));

        User signedUpUser = userRepository.save(newUser);

        UserDto userDto;
        userDto = UserDto.from(signedUpUser);
        return userDto;
    }

}
