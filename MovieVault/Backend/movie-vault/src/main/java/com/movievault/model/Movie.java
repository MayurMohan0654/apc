package com.movievault.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "movies")
public class Movie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String title;
    
    private String genre;
    
    @Column(nullable = false)
    private Integer duration; // in minutes
    
    private String language;
    
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    
    private Double rating;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private String imageUrl; // Store TMDB poster URL
    
    public Movie() {
    }
    

    public Movie(String title, String genre, Integer duration, String language, Date releaseDate, Double rating, String description, String imageUrl) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.language = language;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.description = description;
        this.imageUrl = imageUrl;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public Integer getDuration() {
        return duration;
    }
    
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public Date getReleaseDate() {
        return releaseDate;
    }
    
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    public Double getRating() {
        return rating;
    }
    
    public void setRating(Double rating) {
        this.rating = rating;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    @Override
    public String toString() {
        return "Movie{" +
                "id= " + id +
                ", title= " + title + 
                ", genre= " + genre + 
                ", duration= " + duration +
                ", language= " + language  +
                ", releaseDate= " + releaseDate +
                ", rating= " + rating + 
                ", imageUrl= " + imageUrl + 
                '}';
    }
}
