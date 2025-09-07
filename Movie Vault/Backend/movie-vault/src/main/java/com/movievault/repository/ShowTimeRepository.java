package com.movievault.repository;

import com.movievault.model.ShowTime;
import com.movievault.model.Theater;
import com.movievault.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {
    List<ShowTime> findByTheater(Theater theater);
    List<ShowTime> findByMovie(Movie movie);
    List<ShowTime> findByTheaterId(Long theaterId);
    List<ShowTime> findByMovieId(Long movieId);
}
