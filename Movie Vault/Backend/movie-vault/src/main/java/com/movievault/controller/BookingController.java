package com.movievault.controller;

import com.movievault.model.Booking;
import com.movievault.model.ShowTime;
import com.movievault.model.User;
import com.movievault.repository.BookingRepository;
import com.movievault.repository.ShowTimeRepository;
import com.movievault.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ShowTimeRepository showTimeRepository;
    
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Map<String, Object> bookingRequest) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Long userId = Long.valueOf(bookingRequest.get("userId").toString());
            Long showTimeId = Long.valueOf(bookingRequest.get("showTimeId").toString());
            
            Optional<User> userOpt = userRepository.findById(userId);
            Optional<ShowTime> showTimeOpt = showTimeRepository.findById(showTimeId);
            
            if (!userOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "User not found");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (!showTimeOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Show time not found");
                return ResponseEntity.badRequest().body(response);
            }
            
            Booking booking = new Booking();
            booking.setUser(userOpt.get());
            booking.setShowTime(showTimeOpt.get());
            
            Booking savedBooking = bookingRepository.save(booking);
            
            response.put("success", true);
            response.put("message", "Booking created successfully");
            response.put("booking", savedBooking);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error creating booking: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserBookings(@PathVariable Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        return ResponseEntity.ok(bookings);
    }
    
    @GetMapping("/showtime/{showTimeId}")
    public ResponseEntity<?> getShowTimeBookings(@PathVariable Long showTimeId) {
        List<Booking> bookings = bookingRepository.findByShowTimeId(showTimeId);
        return ResponseEntity.ok(bookings);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Long id) {
        Optional<Booking> bookingOpt = bookingRepository.findById(id);
        
        if (bookingOpt.isPresent()) {
            return ResponseEntity.ok(bookingOpt.get());
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Booking not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            response.put("success", true);
            response.put("message", "Booking cancelled successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Booking not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
