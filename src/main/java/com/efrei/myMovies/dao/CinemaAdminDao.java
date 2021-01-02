package com.efrei.myMovies.dao;

import com.efrei.myMovies.entity.CinemaAdmin;
import com.efrei.myMovies.entity.Movie;

import java.util.List;

public interface CinemaAdminDao  {
    CinemaAdmin findAdmin(String username, String password);

    CinemaAdmin findAdmin(int adminId);

    List<Movie> findMovies(int adminId);
}
