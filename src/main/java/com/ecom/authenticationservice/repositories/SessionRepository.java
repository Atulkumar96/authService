package com.ecom.authenticationservice.repositories;

import com.ecom.authenticationservice.models.Session;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
