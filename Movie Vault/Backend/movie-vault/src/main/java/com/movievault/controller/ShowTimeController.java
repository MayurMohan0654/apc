package com.movievault.controller;

import com.movievault.model.Movie;
import com.movievault.model.ShowTime;
import com.movievault.model.Theater;
import com.movievault.repository.MovieRepository;
import com.movievault.repository.ShowTimeRepository;
import com.movievault.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/showtimes")
public class ShowTimeController {
    
    @Autowired
    private ShowTimeRepository showTimeRepository;
    
    @Autowired
    private TheaterRepository theaterRepository;
    
    @Autowired
    private MovieRepository movieRepository;
    
    // Add a new showtime (for theater admin)
    @PostMapping
    public ResponseEntity<?> addShowTime(@RequestBody Map<String, Object> showTimeRequest) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Long theaterId = Long.valueOf(showTimeRequest.get("theaterId").toString());
            Long movieId = Long.valueOf(showTimeRequest.get("movieId").toString());
            String startTimeStr = showTimeRequest.get("startTime").toString();
            String endTimeStr = showTimeRequest.get("endTime").toString();
            
            Optional<Theater> theaterOpt = theaterRepository.findById(theaterId);
            Optional<Movie> movieOpt = movieRepository.findById(movieId);
            
            if (!theaterOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Theater not found");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (!movieOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Movie not found");
                return ResponseEntity.badRequest().body(response);
            }
            
            ShowTime showTime = new ShowTime();
            showTime.setTheater(theaterOpt.get());
            showTime.setMovie(movieOpt.get());
            
            java.time.LocalDateTime startTime = java.time.LocalDateTime.parse(startTimeStr);
            java.time.LocalDateTime endTime = java.time.LocalDateTime.parse(endTimeStr);
            
            showTime.setStartTime(startTime);
            showTime.setEndTime(endTime);
            
            ShowTime savedShowTime = showTimeRepository.save(showTime);
            
            response.put("success", true);
            response.put("showTime", savedShowTime);
            response.put("message", "Showtime added successfully");
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error adding showtime: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // Get all showtimes (for users)
    @GetMapping
    public ResponseEntity<?> getAllShowTimes() {
        List<ShowTime> showTimes = showTimeRepository.findAll();
        return ResponseEntity.ok(showTimes);
    }
    
    // Get showtimes by theater ID
    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<?> getShowTimesByTheater(@PathVariable Long theaterId) {
        List<ShowTime> showTimes = showTimeRepository.findByTheaterId(theaterId);
        return ResponseEntity.ok(showTimes);
    }
    
    // Get showtimes by movie ID
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<?> getShowTimesByMovie(@PathVariable Long movieId) {
        List<ShowTime> showTimes = showTimeRepository.findByMovieId(movieId);
        return ResponseEntity.ok(showTimes);
    }
}
