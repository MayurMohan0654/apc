package com.movievault.repository;

import com.movievault.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
    
    // Find theater by name
    Optional<Theater> findByName(String name);
    
    // Check if theater exists by name
    boolean existsByName(String name);
    
    // Find theaters by city
    java.util.List<Theater> findByCity(String city);
}
