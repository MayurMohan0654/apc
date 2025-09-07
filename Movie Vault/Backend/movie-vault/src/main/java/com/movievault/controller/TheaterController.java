package com.movievault.controller;

import com.movievault.model.Theater;
import com.movievault.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/theaters")
public class TheaterController {
    
    @Autowired
    private TheaterRepository theaterRepository;
    
    @PostMapping("/register")
    public ResponseEntity<?> registerTheater(@RequestBody Theater theater) {
        Map<String, Object> response = new HashMap<>();
        
        // Check if theater name already exists
        if (theaterRepository.existsByName(theater.getName())) {
            response.put("success", false);
            response.put("message", "Theater name already registered");
            return ResponseEntity.badRequest().body(response);
        }
        
        // Save the theater
        Theater savedTheater = theaterRepository.save(theater);
        
        response.put("success", true);
        response.put("message", "Theater registered successfully");
        response.put("theater", savedTheater);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> loginTheater(@RequestBody Map<String, String> loginRequest) {
        String name = loginRequest.get("name");
        String password = loginRequest.get("password");
        
        Map<String, Object> response = new HashMap<>();
        
        // Find theater by name
        Optional<Theater> theaterOpt = theaterRepository.findByName(name);
        
        if (theaterOpt.isPresent()) {
            Theater theater = theaterOpt.get();
            
            // Simple password check
            if (password.equals(theater.getPassword())) {
                response.put("success", true);
                response.put("message", "Login successful");
                response.put("theater", theater);
                return ResponseEntity.ok(response);
            }
        }
        
        response.put("success", false);
        response.put("message", "Invalid theater name or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
