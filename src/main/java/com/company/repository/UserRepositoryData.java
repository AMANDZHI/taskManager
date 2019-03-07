package com.company.repository;

import com.company.model.Task;
import com.company.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryData extends JpaRepository<User, String> {
    Optional<User> findByLogin(String login);
    Long deleteByLogin(String login);
    List<User> findByRole(String role);
}