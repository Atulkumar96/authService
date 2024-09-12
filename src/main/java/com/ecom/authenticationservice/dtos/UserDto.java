package com.ecom.authenticationservice.dtos;

import com.ecom.authenticationservice.models.Role;
import com.ecom.authenticationservice.models.User;
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

    public static UserDto from(User user){
        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
