package com.ecom.authenticationservice.dtos;

import com.ecom.authenticationservice.models.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
public class UserDto {
    private String email;
    private Set<Role> roles = new HashSet<>();
}
