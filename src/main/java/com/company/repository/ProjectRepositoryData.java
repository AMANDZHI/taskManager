package com.company.repository;

import com.company.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryData extends JpaRepository<Project, String> {
    Optional<Project> findByName(String name);
    Long deleteByName(String name);
    List<Project> findByUserId(String userId);

    @Query("Select p from Project p Where p.id = :id AND p.user.id = :userId")
    Optional<Project> findByIdAndUserId(@Param("id")String id, @Param("userId")String userId);

    @Query("Select p from Project p Where p.name = :name AND p.user.id = :userId")
    Optional<Project> findByNameAndUserId(@Param("name")String name, @Param("userId")String userId);

    @Query("Select p from Project p where date >= :startDate AND date <= :endDate")
    List<Project> findByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
