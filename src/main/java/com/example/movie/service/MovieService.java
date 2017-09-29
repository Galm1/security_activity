package com.example.movie.service;

import com.example.movie.domain.Director;
import com.example.movie.domain.Movie;

import java.util.List;

public interface MovieService {
    Movie add(Movie movie);
    Movie getById(int id);
    List<Movie> getAll();
    void update(Movie movie);
    void delete(int id);

    Movie addDirector(Director director);
    Movie deleteDirector(int movieId, int directorId);
}
