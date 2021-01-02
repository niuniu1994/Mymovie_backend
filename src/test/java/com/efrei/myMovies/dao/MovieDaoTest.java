package com.efrei.myMovies.dao;

import com.efrei.myMovies.entity.Movie;
import com.efrei.myMovies.entity.Session;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MovieDaoTest {

    @Autowired
    MovieDao movieDao;

    @Autowired
    CinemaAdminDao cinemaAdminDao;

    @Test
    public void testFindAllMovies() {
        List<Movie> movies = movieDao.findAllMovies();
        movies.forEach(a -> System.out.println(a));
    }


    @Test
    public void testAddMovie() {
        Session session1 = new Session();
        session1.setDay("Monday");
        session1.setTime(LocalTime.now());


        Movie movie = new Movie();
        movie.setActors("json");
//        movie.setAdmin(cinemaAdminDao.findAdmin("admin1","000"));
        movie.setDirector("dwad");
        movie.setDuration(123);
        movie.setEndDate(LocalDate.now());
        movie.setStartDate(LocalDate.now());
        movie.setLanguage("English");
        movie.setMinAge(20);
        movie.setSubtitle("None");
        movie.setTitle("hello");
        movie.addSession(session1);

        movieDao.addMovie(movie);
    }

    @Test
    public void testFindMoviesByCity(){
        List<Movie> movies = movieDao.findMoviesByCity("New York");
        Assert.assertEquals(2,movies.size());
    }
}
