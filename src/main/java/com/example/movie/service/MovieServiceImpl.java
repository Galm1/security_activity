package com.example.movie.service;

import com.example.movie.domain.Director;
import com.example.movie.domain.Movie;
import com.example.movie.respository.DirectorRepository;
import com.example.movie.respository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Transactional
    @Override
    public Movie add(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie getById(int id) {
        return movieRepository.findOne(id);
    }

    @Override
    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    @Transactional
    @Override
    public void update(Movie movie) {
        movieRepository.save(movie);
    }

    @Transactional
    @Override
    public void delete(int id) {
        movieRepository.delete(id);
    }

    @Transactional
    @Override
    public Movie addDirector(Director director) {
        director = directorRepository.save(director);
        Movie movie = movieRepository.findOne(director.getMovie().getId());
        movie.setDirector(director);
        movieRepository.save(movie);

        return getMovie(movie.getId());
    }

    @Transactional
    @Override
    public Movie deleteDirector(int movieId, int directorId) {
        directorRepository.delete(directorId);
        Movie movie = movieRepository.findOne(movieId);
        movie.setDirector(null);
        movieRepository.save(movie);

        return getMovie(movieId);
    }

    private Movie getMovie(int id) {
        Movie movie = movieRepository.findOne(id);
        return movie;
    }

}
