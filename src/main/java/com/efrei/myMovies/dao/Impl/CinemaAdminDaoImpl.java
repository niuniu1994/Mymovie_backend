package com.efrei.myMovies.dao.Impl;

import com.efrei.myMovies.dao.CinemaAdminDao;
import com.efrei.myMovies.entity.CinemaAdmin;
import com.efrei.myMovies.entity.Movie;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author kainingxin
 */
@Repository("cinemaAdminDao")
public class CinemaAdminDaoImpl implements CinemaAdminDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public CinemaAdmin findAdmin(String username, String password) {
        String queryString = "select admin from CinemaAdmin  admin  where admin.username=:username and admin.password=:password";
        return (CinemaAdmin) entityManager.createQuery(queryString).setParameter("username", username).setParameter("password", password).getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public CinemaAdmin findAdmin(int adminId) {
        return entityManager.find(CinemaAdmin.class, adminId);
    }

    @Override
    public List<Movie> findMovies(int adminId) {
        String queryString = "select admin.movieList from CinemaAdmin admin where admin.adminId=:adminId";
        return entityManager.createQuery(queryString).setParameter("adminId",adminId).getResultList();
    }


}
