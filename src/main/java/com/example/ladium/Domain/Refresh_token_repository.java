package com.example.ladium.Domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Refresh_token_repository extends JpaRepository<Refresh_token, String> {
    @Query(value = "SELECT * FROM refresh_token WHERE refresh_key = ?1", nativeQuery = true)
    Optional<Refresh_token> findByRefresh_key(@Param("refresh_key") String refresh_key);
}
