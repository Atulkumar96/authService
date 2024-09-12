package com.ecom.authenticationservice.repositories;

import com.ecom.authenticationservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
