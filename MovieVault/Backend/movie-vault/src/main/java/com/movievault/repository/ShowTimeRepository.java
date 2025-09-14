package com.movievault.repository;

import com.movievault.model.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {
List<ShowTime> findByMovie_Id(Long movieId);
List<ShowTime> findByTheater_Id(Long theaterId);
List<ShowTime> findByMovie_IdAndTheater_Id(Long movieId, Long theaterId);
}
