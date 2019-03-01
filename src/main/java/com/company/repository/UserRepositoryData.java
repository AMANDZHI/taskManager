package com.company.repository;

import com.company.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryData extends JpaRepository<User, String> {
    Optional<User> findByLogin(String login);
    Long deleteByLogin(String login);
}