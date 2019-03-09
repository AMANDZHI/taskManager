package com.company.repository;

import com.company.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRepositoryData extends JpaRepository<User, String> {
    Optional<User> findByLogin(String login);
    Long deleteByLogin(String login);

    @Query("Select u from User u where date >= :startDate AND date <= :endDate")
    List<User> findByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}