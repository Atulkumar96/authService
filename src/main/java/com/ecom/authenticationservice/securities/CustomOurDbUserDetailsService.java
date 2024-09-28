package com.ecom.authenticationservice.securities;

import com.ecom.authenticationservice.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.ecom.authenticationservice.models.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOurDbUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomOurDbUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(username+" not found");
        }
        User user = userOptional.get();


        return new CustomOurDbUserDetails(user);
    }
}
