package com.movievault.model;

import lombok.Data;
import java.util.List;

@Data
public class TMDBResponse {
    private int page;
    private List<TMDBMovie> results;
    private int total_results;
    private int total_pages;
}
