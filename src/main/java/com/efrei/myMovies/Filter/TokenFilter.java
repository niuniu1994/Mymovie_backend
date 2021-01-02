package com.efrei.myMovies.Filter;

import com.efrei.myMovies.util.TokenProcessor;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.net.URI;
import java.util.Map;


/**
 * fiter that parse token and if token exist parse token to get admin info stored, at last put admin into requestContext
 */
@Component
@Provider
public class TokenFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        URI uri = requestContext.getUriInfo().getRequestUri();
        if (uri.getPath().contains("admin/movie")) {
            String token = requestContext.getHeaders().getFirst("Authorization");
            if (token != null){
                Map<String, Object> map = TokenProcessor.parseToken(token);
                if (map.get("admin") != null){
                    requestContext.setProperty("admin",map.get("admin"));
                }
            }
        }
    }
}
