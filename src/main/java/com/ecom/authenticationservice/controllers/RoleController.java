package com.ecom.authenticationservice.controllers;

import com.ecom.authenticationservice.dtos.CreateRoleDto;
import com.ecom.authenticationservice.models.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class RoleController {

    @PostMapping("/roles")
    private ResponseEntity<Role> createRole(CreateRoleDto createRoleDto) {
        return null; ////private method check
    }
}
