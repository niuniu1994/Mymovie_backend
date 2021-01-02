package com.efrei.myMovies.dao;

import com.efrei.myMovies.entity.Movie;


import java.util.List;

public interface MovieDao {
    List<Movie> findAllMovies();

    List<Movie> findMoviesByCity(String city);

   int addMovie(Movie movie);
}
