package com.example.movie.controller;

import com.example.movie.domain.Director;
import com.example.movie.domain.Movie;
import com.example.movie.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    // Json to Java
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Add movie
     */
    @PostMapping("/api/movies")
    public Movie addMovie(@RequestBody String json) throws IOException {
        Movie movie = objectMapper.readValue(json, Movie.class);
        return movieService.add(movie);
    }

    /**
     * Update movie
     */
    @PutMapping("/api/movies/{id}")
    public String updateMovie(@PathVariable("id") Integer id, @RequestBody String json) throws IOException {
        Movie movie = objectMapper.readValue(json, Movie.class);
        movie.setId(id);
        movieService.update(movie);
        return "ok";
    }

    /*
     * Get all movies
     */
    @GetMapping("/api/movies")
    public List<Movie> getMovies() {
        return movieService.getAll();
    }

    /*
     * Get movie for id
     */
    @GetMapping("/api/movies/{id}")
    public Movie getMovie(@PathVariable("id") Integer id) {
        return movieService.getById(id);
    }

    /**
     * Delete the movie for the id
     */
    @DeleteMapping("/api/movies/{id}")
    public String deleteMovie(@PathVariable("id") Integer id) {
        movieService.delete(id);
        return "ok";
    }

    /**
     * Add a director
     */
    @PostMapping("/api/movie/{id}/director")
    public Movie addDirector(@PathVariable("id") Integer id,
                             @RequestBody String json) throws IOException {
        Director director = objectMapper.readValue(json, Director.class);
        Movie movie = movieService.getById(id);
        director.setMovie(movie);
        return movieService.addDirector(director);
    }

    /**
     * Deletes the director from the movie. The url has both ids.
     */
    @DeleteMapping("/api/movie/{id}/director/{directorId}")
    public Movie deleteDirector(@PathVariable("id") Integer id,
                                @PathVariable("directorId") Integer directorId) {
        return movieService.deleteDirector(id, directorId);
    }
}
