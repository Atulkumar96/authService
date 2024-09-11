package com.ecom.authenticationservice.controllers;

import com.ecom.authenticationservice.dtos.SetUserRolesDto;
import com.ecom.authenticationservice.dtos.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") Long userId){
        return new ResponseEntity<>(HttpStatus.OK);
        //return null;
    }

    @PostMapping("/roles/{id}")
    public ResponseEntity<UserDto> setUserRoles(@PathVariable("id") Long userId , @RequestBody SetUserRolesDto setUserRolesDto){
        return null;
    }
}
