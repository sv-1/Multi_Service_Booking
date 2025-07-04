package com.yourcompany.booking.service;

import com.yourcompany.booking.model.User;
import com.yourcompany.booking.model.enums.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {


    void registerUser(User user);


    boolean existsByEmail(String email);


    User getByEmail(String email);


    User getUserByEmail(String email);


    User getUserById(Long id);


    List<User> getAllUsers();


    List<User> getUsersByRole(Role role);


    void deleteUser(Long id);


    User saveUser(User user);


    Role getCurrentUserRole();


    long countUsers();


    User findByEmail(String email);


    Role getRoleByEmail(String email);
}
