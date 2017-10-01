package com.example.movie.controller;

import com.example.movie.domain.Director;
import com.example.movie.domain.Movie;
import com.example.movie.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    private ObjectMapper objectMapper = new ObjectMapper();


    @PostMapping("/movies")
    public Movie addMovie(@RequestBody String json) throws IOException {
        Movie movie = objectMapper.readValue(json, Movie.class);
        return movieService.add(movie);
    }

    @PutMapping("/movies/{id}")
    public String updateMovie(@PathVariable("id") Integer id, @RequestBody String json) throws IOException {
        Movie movie = objectMapper.readValue(json, Movie.class);
        movie.setId(id);
        movieService.update(movie);
        return "view_movies";
    }

    @GetMapping("/movies")
    public List<Movie> getMovies() {
        return movieService.getAll();
    }

    @GetMapping("/movies/{id}")
    public Movie getMovie(@PathVariable("id") Integer id) {
        return movieService.getById(id);
    }


    @DeleteMapping("/movies/{id}")
    public String deleteMovie(@PathVariable("id") Integer id) {
        movieService.delete(id);
        return "ok";
    }


    @PostMapping("/movie/{id}/director")
    public Movie addDirector(@PathVariable("id") Integer id,
                             @RequestBody String json) throws IOException {
        Director director = objectMapper.readValue(json, Director.class);
        Movie movie = movieService.getById(id);
        director.setMovie(movie);
        return movieService.addDirector(director);
    }


    @DeleteMapping("/movie/{id}/director/{directorId}")
    public Movie deleteDirector(@PathVariable("id") Integer id,
                                @PathVariable("directorId") Integer directorId) {
        return movieService.deleteDirector(id, directorId);
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @RequestMapping("/loggedout")
    String logout(Model model) {
        List<Movie> movies = movieService.getAll();
        model.addAttribute("listOfMovies", movies);
        return "view_movies";
    }

    @GetMapping("/admins-only")
    String admins() {
        return "administration";
    }

    @ExceptionHandler(value = Exception.class)
    public String handleDefaultErrors(final Exception exception, Model model) {
        System.out.println(exception);
        model.addAttribute("message", exception.getMessage());
        return "error_message";
    }
}
