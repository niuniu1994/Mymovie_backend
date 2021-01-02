package com.efrei.myMovies.webResource;

import com.efrei.myMovies.dao.CinemaAdminDao;
import com.efrei.myMovies.dao.MovieDao;
import com.efrei.myMovies.entity.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Path("resource")
public class MovieResource {

    @Autowired
    MovieDao movieDao;

    @Autowired
    CinemaAdminDao cinemaAdminDao;

    @Path("movies/{city}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMoviesByCity(@PathParam("city") String city) throws JsonProcessingException {
        if (city == null) {
            return "{\"msg\":\"city name null\"}";
        }
        city = city.replace("_", " ").trim();
        List<Movie> movieList = movieDao.findMoviesByCity(city);

        Map<String, Object> moviesMap = new HashMap<>();
        moviesMap.put("movies", movieList);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(moviesMap);
    }

}
