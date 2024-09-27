package com.ecom.authenticationservice.services;

import com.ecom.authenticationservice.dtos.LoginRequestDto;
import com.ecom.authenticationservice.dtos.SignUpRequestDto;
import com.ecom.authenticationservice.dtos.UserDto;
import com.ecom.authenticationservice.dtos.ValidateTokenRequestDto;
import com.ecom.authenticationservice.exceptions.InvalidTokenException;
import com.ecom.authenticationservice.models.Session;
import com.ecom.authenticationservice.models.SessionStatus;
import com.ecom.authenticationservice.models.User;
import com.ecom.authenticationservice.repositories.SessionRepository;
import com.ecom.authenticationservice.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
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
            throw new BadCredentialsException("Wrong UserId or password");
        }

        //Email & Password matched
        //Generate Token & Create User's Session
        //Set Token in HttpHeaders.SET_COOKIE & return the ResponseEntity
        //String token = RandomStringUtils.randomAlphanumeric(30); //Used Apache commons library

        //Algo & Key - should be in properties/environment variable
        MacAlgorithm algo = Jwts.SIG.HS256;
        SecretKey key = algo.key().build();

        //payload data in JWT (JSON Web Token)
        Map<String, Object> jsonJWTPayload = new HashMap<>();
        jsonJWTPayload.put("Email", user.getEmail());
        jsonJWTPayload.put("createdAt", new Date());
        jsonJWTPayload.put("expiryAt", new Date(LocalDate.now().plusDays(7).toEpochDay()));

        //"Build the JWT Token" - using its "signing algo & payload"
        String token = Jwts.builder().claims(jsonJWTPayload).signWith(key, algo).compact();

        Session session = new Session();
        session.setToken(token);
        session.setUser(user);
        session.setSessionStatus(SessionStatus.ACTIVE);

            long millisInAday = 1000*60*60*24;
            Date date = new Date();
        session.setExpiryDate(new Date(date.getTime()+ millisInAday)); //next day expires from now

        sessionRepository.save(session);

        //Set Token in HttpHeaders.SET_COOKIE & return via ResponseEntity
        MultiValueMap<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:"+token);
//        Map<String,String> headers = new HashMap<>();
//        headers.put(HttpHeaders.SET_COOKIE, "auth-token: "+token);

        return new ResponseEntity<>(UserDto.from(user),headers, HttpStatus.OK);
    }

    @Override //Validate Token Request will come from API Gateway/Kong
    public SessionStatus validateToken(ValidateTokenRequestDto validateTokenRequestDto) throws InvalidTokenException {
        Optional<Session> session = sessionRepository.findByTokenAndUser_Id(validateTokenRequestDto.getToken(), validateTokenRequestDto.getUserId());
        // 1. Invalid Token Exception Handling
        if (session.isEmpty()) {
            throw new InvalidTokenException("Invalid token, Continuous Spamming will block your IP");
        }

        //2. If session exists with the provided token then have the jws(JSON Web Signature) claims
        Jws<Claims> jwsClaims = Jwts.parser().build().parseSignedClaims(validateTokenRequestDto.getToken());

        String email = (String) jwsClaims.getPayload().get("Email");
        Date createdAt = (Date) jwsClaims.getPayload().get("createdAt");
        Date expiryAt = (Date) jwsClaims.getPayload().get("expiryAt");

        //3. Check the createdAt and other payload data
        // & return the SessionStatus accordingly

        return SessionStatus.ACTIVE;
    }

}
