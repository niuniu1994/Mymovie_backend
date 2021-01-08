package com.efrei.myMovies.webResource;

import com.efrei.myMovies.constant.ErrorCode;
import com.efrei.myMovies.dao.CinemaAdminDao;
import com.efrei.myMovies.dao.MovieDao;
import com.efrei.myMovies.dto.Response;
import com.efrei.myMovies.entity.CinemaAdmin;
import com.efrei.myMovies.entity.Movie;
import com.efrei.myMovies.entity.Session;
import com.efrei.myMovies.util.TokenProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Path("admin")
public class AdminResource {

    @Autowired
    private CinemaAdminDao cinemaAdminDao;

    @Autowired
    private MovieDao movieDao;


    @Path("movie")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovies(@Context ContainerRequestContext requestContext) {
        Response response = new Response();
        //get admin info stored in the requestContext
        LinkedHashMap<String, Object> admin = (LinkedHashMap<String, Object>) requestContext.getProperty("admin");
        if (admin != null) {
            List<Movie> movies = cinemaAdminDao.findMovies((Integer) admin.get("adminId"));
            Map<String, Object> moviesMap = new HashMap<>();
            moviesMap.put("movies", movies);
            moviesMap.put("msg", "Success");
            response.setErrorMsg(ErrorCode.SUCCESS.getErrorMsg());
            response.setData(moviesMap);
            return response;
        }
        response.setErrorCode(ErrorCode.NO_PERMISSION.getErrorCode());
        response.setErrorMsg(ErrorCode.NO_PERMISSION.getErrorMsg());
        return response;
    }

    @Path("movie")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMovie(@Context ContainerRequestContext requestContext, Movie movie) {
        Response response = new Response();

        if (movie != null) {

            LinkedHashMap<String, Object> admin = (LinkedHashMap<String, Object>) requestContext.getProperty("admin");
            if (admin != null) {
                movie.setAdmin(cinemaAdminDao.findAdmin((Integer) admin.get("adminId")));
                List<Session> sessions = movie.getSessionList();
                for (Session session : sessions){
                    session.setMovie(movie);
                }
                int movieId = movieDao.addMovie(movie);
                if (movieId > 0) {
                    response.setErrorMsg(ErrorCode.SUCCESS.getErrorMsg());
                    return response;
                }
                response.setErrorCode(ErrorCode.FAILED.getErrorCode());
                response.setErrorMsg(ErrorCode.FAILED.getErrorMsg());
                return response;
            }
            response.setErrorCode(ErrorCode.NO_PERMISSION.getErrorCode());
            response.setErrorMsg(ErrorCode.NO_PERMISSION.getErrorMsg());
            return response;
        }
        response.setErrorCode(ErrorCode.NULLOBJECT.getErrorCode());
        response.setErrorMsg(ErrorCode.NULLOBJECT.getErrorMsg());
        return response;

    }

    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@Context HttpServletRequest request, JsonObject jsonObject) throws JsonProcessingException {
        Response response = new Response();
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        if (!username.isEmpty() && !password.isEmpty()) {
            CinemaAdmin admin = cinemaAdminDao.findAdmin(username, password);
            if (admin != null) {
                Map<String, String> map = new HashMap<>();
                Map<String, Object> map1 = Maps.newHashMap();
                map1.put("admin", admin);

                //generate token stored data of admin logged in
                String token = TokenProcessor.makeToken(map1);

                map.put("token", token);

                response.setErrorMsg(ErrorCode.SUCCESS.getErrorMsg());
                response.setData(map);

                return response;
            }
            response.setErrorCode(ErrorCode.FAILED.getErrorCode());
            response.setErrorMsg(ErrorCode.FAILED.getErrorMsg());
            return response;
        }
        response.setErrorCode(ErrorCode.NULLOBJECT.getErrorCode());
        response.setErrorCode(ErrorCode.NULLOBJECT.getErrorCode());
        return response;
    }
    
}
