package com.movievault.controller;

import com.movievault.model.Movie;
import com.movievault.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    
    @Autowired
    private MovieRepository movieRepository;
    
    // Get all movies
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    
    // Get movie by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        
        if (movie.isPresent()) {
            return ResponseEntity.ok(movie.get());
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Movie not found with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
    // Add a new movie
    @PostMapping
    public ResponseEntity<?> addMovie(@RequestBody Movie movie) {
        try {
            Movie savedMovie = movieRepository.save(movie);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Movie added successfully");
            response.put("movie", savedMovie);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error adding movie: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    // Update an existing movie
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id, @RequestBody Movie movieDetails) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        
        if (!optionalMovie.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Movie not found with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        Movie movie = optionalMovie.get();
        movie.setTitle(movieDetails.getTitle());
        movie.setGenre(movieDetails.getGenre());
        movie.setDuration(movieDetails.getDuration());
        movie.setLanguage(movieDetails.getLanguage());
        movie.setReleaseDate(movieDetails.getReleaseDate());
        movie.setRating(movieDetails.getRating());
        movie.setDescription(movieDetails.getDescription());
        
        try {
            Movie updatedMovie = movieRepository.save(movie);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Movie updated successfully");
            response.put("movie", updatedMovie);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error updating movie: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    // Delete a movie
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        try {
            if (!movieRepository.existsById(id)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Movie not found with id " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            movieRepository.deleteById(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Movie deleted successfully");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error deleting movie: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
 
    @GetMapping("/search")
    public List<Movie> searchMovies(@RequestParam String title) {

        List<Movie> allMovies = movieRepository.findByTitleContainingIgnoreCase(title);
        return allMovies;
    }
    
    // Check if movie exists by exact title match
    @GetMapping("/exists")
    public ResponseEntity<?> checkMovieExists(@RequestParam String title) {
        List<Movie> allMovies = movieRepository.findAll();
        Optional<Movie> existingMovie = allMovies.stream()
                .filter(movie -> movie.getTitle().toLowerCase().trim().equals(title.toLowerCase().trim()))
                .findFirst();
        
        Map<String, Object> response = new HashMap<>();
        if (existingMovie.isPresent()) {
            response.put("exists", true);
            response.put("movie", existingMovie.get());
        } else {
            response.put("exists", false);
        }
        
        return ResponseEntity.ok(response);
    }
}