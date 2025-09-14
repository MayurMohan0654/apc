package com.movievault.repository;

import com.movievault.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findByGenreIgnoreCase(String genre);
    List<Movie> findByLanguageIgnoreCase(String language);
    @Query("SELECT m FROM Movie m WHERE m.rating >= :rating")
    List<Movie> findByMinimumRating(@Param("rating") Double rating);
    @Query("SELECT m FROM Movie m ORDER BY m.releaseDate DESC")
    List<Movie> findLatestMovies();
}
