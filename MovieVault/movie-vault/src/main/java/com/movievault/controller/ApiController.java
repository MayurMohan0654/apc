package com.movievault.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.movievault.model.TMDBMovie;
import com.movievault.model.TMDBResponse;

@RestController
public class ApiController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/api/allMovies")
    public List<TMDBMovie> getMovies(@RequestParam String query) {
        String url = "https://api.themoviedb.org/3/search/movie?api_key=6c420884d135e913472960df62122413&query=" + query;

        TMDBResponse response = restTemplate.getForObject(url, TMDBResponse.class);
        return response.getResults();
    }
}
