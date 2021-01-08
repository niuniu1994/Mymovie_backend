package com.efrei.myMovies.dao.Impl;

import com.efrei.myMovies.dao.MovieDao;
import com.efrei.myMovies.entity.Movie;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("movieDaoImpl")
public class MovieDaoImpl implements MovieDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Movie> findAllMovies() {
        String queryString = "select movie from Movie movie";
        return (List<Movie>) entityManager.createQuery(queryString).getResultList();
    }

    @Override
    public List<Movie> findMoviesByCity(String city) {
        String queryString = "select admin.adminId from CinemaAdmin admin where lower(admin.city)  = lower(:city) ";
        List<Integer> cinemaAdminIds = (List<Integer>) entityManager.createQuery(queryString).setParameter("city", city).getResultList();

        String queryString1 = "select movie from Movie movie where movie.admin.adminId in (:idList)";
        return (List<Movie>) entityManager.createQuery(queryString1).setParameter("idList", cinemaAdminIds).getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = RuntimeException.class)
    public int addMovie(Movie movie) {
        entityManager.persist(movie);
        return movie.getMovieId();
    }

}
