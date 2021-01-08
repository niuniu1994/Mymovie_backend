package com.efrei.myMovies;

import com.efrei.myMovies.Filter.CrosFilter;

import com.efrei.myMovies.util.TokenProcessor;
import com.efrei.myMovies.webResource.AdminResource;
import com.efrei.myMovies.webResource.MovieResource;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;


@ApplicationPath("/")
public class MyConfig extends ResourceConfig {
    public MyConfig() {
        this.packages("com.efrei.myMovies");
        this.property("contextConfigLocation","classpath:applicationContext.xml");
        this.register(JacksonFeature.class);
        this.register(CrosFilter.class);
        this.register(AdminResource.class);
        this.register(MovieResource.class);
        this.register(TokenProcessor.class);
    }
}
