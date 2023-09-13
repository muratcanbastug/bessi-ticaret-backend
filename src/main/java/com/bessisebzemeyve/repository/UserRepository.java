package com.bessisebzemeyve.repository;

import com.bessisebzemeyve.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAllByUsernameContainsOrderByUsername(String name);
}
