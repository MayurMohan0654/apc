package com.movievault.repository;

import com.movievault.model.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {
    
    // Find showtimes by movie ID
    @Query("SELECT s FROM ShowTime s WHERE s.movie.id = :movieId")
    List<ShowTime> findByMovieId(@Param("movieId") Long movieId);
    
    // Find showtimes by theater ID
    @Query("SELECT s FROM ShowTime s WHERE s.theater.id = :theaterId")
    List<ShowTime> findByTheaterId(@Param("theaterId") Long theaterId);
    
    // Find showtimes by movie ID and theater ID
    @Query("SELECT s FROM ShowTime s WHERE s.movie.id = :movieId AND s.theater.id = :theaterId")
    List<ShowTime> findByMovieIdAndTheaterId(@Param("movieId") Long movieId, @Param("theaterId") Long theaterId);
}
