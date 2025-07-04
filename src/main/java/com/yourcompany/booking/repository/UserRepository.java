package com.yourcompany.booking.repository;

import com.yourcompany.booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);


    boolean existsByEmail(String email);


    List<User> findByRole(com.yourcompany.booking.model.enums.Role role);
}
