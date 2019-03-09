package com.company.repository;

import com.company.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TaskRepositoryData extends JpaRepository<Task, String> {
    Optional<Task> findByName(String name);
    Long deleteByName(String name);
    List<Task> findByUserId(String userId);

    @Query("Select t from Task t Where t.id = :id AND t.user.id = :userId")
    Optional<Task> findByIdAndUserId(@Param("id")String id, @Param("userId")String userId);

    @Query("Select t from Task t Where t.name = :name AND t.user.id = :userId")
    Optional<Task> findByNameAndUserId(@Param("name")String name, @Param("userId")String userId);

    @Query("Delete from Task t Where t.name = :name AND t.user.id = :userId")
    @Modifying
    Integer deleteByNameAndUserId(@Param("name")String name, @Param("userId")String userId);

    @Query("Delete from Task t Where t.id = :id AND t.user.id = :userId")
    @Modifying
    Integer deleteByIdAndUserId(@Param("id")String id, @Param("userId")String userId);

    @Query("Select t from Task t where date >= :startDate AND date <= :endDate")
    List<Task> findByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
