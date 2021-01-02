package com.efrei.myMovies.webResource;

import com.efrei.myMovies.dao.CinemaAdminDao;
import com.efrei.myMovies.dao.MovieDao;
import com.efrei.myMovies.entity.CinemaAdmin;
import com.efrei.myMovies.entity.Movie;
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
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Path("admin")
public class AdminResource {

    @Autowired
    CinemaAdminDao cinemaAdminDao;

    @Autowired
    MovieDao movieDao;

    @Path("movie")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMovies(@Context HttpServletRequest request, @Context ContainerRequestContext requestContext) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        //get admin info stored in the requestContext
        LinkedHashMap<String, Object> admin = (LinkedHashMap<String, Object>) requestContext.getProperty("admin");
        if (admin != null) {
            List<Movie> movies = cinemaAdminDao.findMovies((Integer) admin.get("adminId"));
            Map<String, Object> moviesMap = new HashMap<>();
            moviesMap.put("movies", movies);
            moviesMap.put("msg", "Success");
            return objectMapper.writeValueAsString(moviesMap);
        }
        return "{\"msg\":\"No permission\"}";
    }

    @Path("movie")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addMovie(@Context ContainerRequestContext requestContext, String jsonObject) {
        if (jsonObject != null) {

            ObjectMapper objectMapper = new ObjectMapper();
            Movie movie = null;
            if (jsonObject != null) {
                try {
                    movie = objectMapper.readValue(jsonObject, Movie.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "{\"msg\":\"Json format incorrect\"}";
                }
            }

            LinkedHashMap<String, Object> admin = (LinkedHashMap<String, Object>) requestContext.getProperty("admin");
            if (admin != null) {
                movie.setAdmin(cinemaAdminDao.findAdmin((Integer) admin.get("adminId")));
                int movieId = movieDao.addMovie(movie);
                if (movieId > 0) {
                    return "{\"msg\":\"Success\"}";
                }
                return "{\"msg\":\"Failed\"}";
            }
            return "{\"msg\":\"No permission\"}";
        }
        return "{\"msg\":\"Object is null\"}";

    }

    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String login(@Context HttpServletRequest request, JsonObject jsonObject) throws JsonProcessingException {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        if (!username.isEmpty() && !password.isEmpty()) {
            CinemaAdmin admin = cinemaAdminDao.findAdmin(username, password);
            if (admin != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> map = new HashMap<>();
                Map<String, Object> map1 = Maps.newHashMap();
                map1.put("admin", admin);

                //generate token stored data of admin logged in
                String token = TokenProcessor.makeToken(map1);

                map.put("msg", "Success");
                map.put("token", token);

                return objectMapper.writeValueAsString(map);
            }
            return "{\"msg\":\"Failed\"}";
        }
        return "{\"msg\":\"object is null\"}";
    }
    
}
