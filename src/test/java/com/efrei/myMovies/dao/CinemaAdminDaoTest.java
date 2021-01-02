package com.efrei.myMovies.dao;

import com.efrei.myMovies.entity.CinemaAdmin;
import com.efrei.myMovies.entity.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CinemaAdminDaoTest {

    @Autowired
    CinemaAdminDao cinemaAdminDao;

    @Test
    public void testFindCinemaAdmin() {
        CinemaAdmin cinemaAdmin = cinemaAdminDao.findAdmin("admin0", "000");
    }

    @Test
    public void testMovies() throws JsonProcessingException {
        List<Movie> movies = cinemaAdminDao.findMovies(1);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValueAsString(movies);
        movies.forEach(System.out::println);
    }
}
