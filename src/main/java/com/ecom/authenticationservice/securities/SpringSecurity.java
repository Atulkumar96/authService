package com.ecom.authenticationservice.securities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//      http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated()); //authenticate all requests
//      http.authorizeRequests(authorize -> authorize.requestMatchers("/auth").authenticated()); //auth controller authentication required
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll()); // permits all requests

        return http.build();
    }

}

// "SecurityFilterChain" object: customize what all "api endpoints should be authenticated"
// v/s what all shouldn't be authenticated
