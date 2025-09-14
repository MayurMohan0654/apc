package com.movievault.controller;

import com.movievault.model.ShowTime;
import com.movievault.model.Movie;
import com.movievault.model.Theater;
import com.movievault.repository.ShowTimeRepository;
import com.movievault.repository.MovieRepository;
import com.movievault.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/showtimes")
public class ShowTimeController {
    
    @Autowired
    private ShowTimeRepository showTimeRepository;
    
    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private TheaterRepository theaterRepository;
    
  
    @GetMapping
    public List<ShowTime> getAllShowTimes() {
        
        List<ShowTime> allShowtimes = showTimeRepository.findAll();
        System.out.println("Found " + allShowtimes.size() + " showtimes in database");
        for (ShowTime showTime : allShowtimes) {
            System.out.println("ShowTime ID: " + showTime.getId() + 
                             ", Movie: " + (showTime.getMovie() != null ? showTime.getMovie().getTitle() : "null") +
                             ", Theater: " + (showTime.getTheater() != null ? showTime.getTheater().getName() : "null"));
        }
        return allShowtimes;
    }
    
 
    @GetMapping("/{id}")
    public ResponseEntity<?> getShowTimeById(@PathVariable Long id) {
        Optional<ShowTime> showTime = showTimeRepository.findById(id);
        
        if (showTime.isPresent()) {
            return ResponseEntity.ok(showTime.get());
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "ShowTime not found with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
  
    @GetMapping("/movie/{movieId}")
    public List<ShowTime> getShowTimesByMovie(@PathVariable Long movieId) {
        List<ShowTime> showtimes = showTimeRepository.findByMovie_Id(movieId);
        System.out.println("Found " + showtimes.size() + " showtimes for movie ID " + movieId);
        return showtimes;
    }
    

    @GetMapping("/theater/{theaterId}")
    public List<ShowTime> getShowTimesByTheater(@PathVariable Long theaterId) {
        List<ShowTime> showtimes = showTimeRepository.findByTheater_Id(theaterId);
        System.out.println("Found " + showtimes.size() + " showtimes for theater ID " + theaterId);
        return showtimes;
    }
    
    @PostMapping
    public ResponseEntity<?> addShowTime(@RequestBody Map<String, Object> request) {
        try {
      
            Long movieId = Long.valueOf(request.get("movieId").toString());
            Long theaterId = Long.valueOf(request.get("theaterId").toString());
            String startTimeStr = request.get("startTime").toString();
            String endTimeStr = request.get("endTime").toString();
            LocalDateTime startTime = LocalDateTime.parse(startTimeStr);
            LocalDateTime endTime = LocalDateTime.parse(endTimeStr);
            Optional<Movie> movieOpt = movieRepository.findById(movieId);
            Optional<Theater> theaterOpt = theaterRepository.findById(theaterId);
            if (!movieOpt.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Movie not found with id " + movieId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            if (!theaterOpt.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Theater not found with id " + theaterId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            // Create ShowTime object
            ShowTime showTime = new ShowTime();
            showTime.setMovie(movieOpt.get());
            showTime.setTheater(theaterOpt.get());
            showTime.setStartTime(Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant()));
            showTime.setEndTime(Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant()));
            
            ShowTime savedShowTime = showTimeRepository.save(showTime);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "ShowTime added successfully");
            response.put("showTime", savedShowTime);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error adding showtime: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    // Update an existing showtime
    @PutMapping("/{id}")
    public ResponseEntity<?> updateShowTime(@PathVariable Long id, @RequestBody ShowTime showTimeDetails) {
        Optional<ShowTime> optionalShowTime = showTimeRepository.findById(id);
        
        if (!optionalShowTime.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "ShowTime not found with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        ShowTime showTime = optionalShowTime.get();
        showTime.setStartTime(showTimeDetails.getStartTime());
        showTime.setEndTime(showTimeDetails.getEndTime());
        showTime.setMovie(showTimeDetails.getMovie());
        showTime.setTheater(showTimeDetails.getTheater());
        
        try {
            ShowTime updatedShowTime = showTimeRepository.save(showTime);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "ShowTime updated successfully");
            response.put("showTime", updatedShowTime);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error updating showtime: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    // Delete a showtime
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShowTime(@PathVariable Long id) {
        try {
            if (!showTimeRepository.existsById(id)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "ShowTime not found with id " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            showTimeRepository.deleteById(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "ShowTime deleted successfully");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error deleting showtime: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
