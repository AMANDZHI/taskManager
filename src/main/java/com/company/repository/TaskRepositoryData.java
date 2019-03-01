package com.company.repository;

import com.company.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepositoryData extends JpaRepository<Task, String> {
    Optional<Task> findByName(String name);
    Long deleteByName(String name);
}
