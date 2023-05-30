package com.lawrenceekale.jwtspringsecurity.repository;

import com.lawrenceekale.jwtspringsecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
