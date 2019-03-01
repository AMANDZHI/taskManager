package com.company.repository;

import com.company.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepositoryData extends JpaRepository<Project, String> {
    Optional<Project> findByName(String name);
    Long deleteByName(String name);
}
