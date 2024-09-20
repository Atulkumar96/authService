package com.ecom.authenticationservice.services;

import com.ecom.authenticationservice.dtos.LoginRequestDto;
import com.ecom.authenticationservice.dtos.SignUpRequestDto;
import com.ecom.authenticationservice.dtos.UserDto;
import com.ecom.authenticationservice.models.Session;
import com.ecom.authenticationservice.models.SessionStatus;
import com.ecom.authenticationservice.models.User;
import com.ecom.authenticationservice.repositories.SessionRepository;
import com.ecom.authenticationservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Service
public class AuthService implements AuthServiceContract{
    //dependencies objects
    private BCryptPasswordEncoder bCryptPasswordEncoder; //for (encrypting+salting) the password
    private UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public AuthService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository,
                       SessionRepository sessionRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
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

    @Override
    public ResponseEntity<UserDto> login(LoginRequestDto loginRequestDto) {
        Optional<User> userOptionalWithEmailinDb = userRepository.findByEmail(loginRequestDto.getEmail());

        if (userOptionalWithEmailinDb.isEmpty()) {
            return null; //No user with the passed email is present
        }

        User user = userOptionalWithEmailinDb.get();
        if(!bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){
            throw new BadCredentialsException("Wrong password");
        }

        //Email & Password matched
        //Generate Token & Create User's Session
        String token = RandomStringUtils.randomAlphanumeric(30); //Used Apache commons library

        Session session = new Session();
        session.setToken(token);
        session.setUser(user);
        session.setSessionStatus(SessionStatus.ACTIVE);

            long millisInAday = 1000*60*60*24;
            Date date = new Date();
        session.setExpiryDate(new Date(date.getTime()+ millisInAday)); //next day expires from now

        sessionRepository.save(session);

        //Set Token in Cookie to return
        MultiValueMap<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:"+token);
//        Map<String,String> headers = new HashMap<>();
//        headers.put(HttpHeaders.SET_COOKIE, "auth-token: "+token);

        return new ResponseEntity<>(UserDto.from(user),headers, HttpStatus.OK);
    }

}
