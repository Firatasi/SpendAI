package com.demo.SpendAI.repository;

import com.demo.SpendAI.entity.Spend;
import com.demo.SpendAI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

}
