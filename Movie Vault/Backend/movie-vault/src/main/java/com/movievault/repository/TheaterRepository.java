package com.movievault.repository;

import com.movievault.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
    Optional<Theater> findByName(String name);
    boolean existsByName(String name);
    java.util.List<Theater> findByCity(String city);
}
