package com.example.ladium.Domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface User_info_repository extends JpaRepository<User_info, String> {
    @Query(value = "SELECT * FROM user WHERE user_id = ?1", nativeQuery = true)
    Optional<User_info> findOneWithAuthoritiesByUser_id(@Param("user_id") String user_id);
}
