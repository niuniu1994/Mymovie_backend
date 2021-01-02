package com.efrei.myMovies.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cinema_admin")
@Getter
@Setter
@NoArgsConstructor
public class CinemaAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private int adminId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(name = "cinema_name", nullable = false)
    private String cinemaName;


    @OneToMany(mappedBy = "admin", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Movie> movieList = new ArrayList<>();

    public void addMovie(Movie movie) {
        movie.setAdmin(this);
        movieList.add(movie);
    }

    public void removeMovie(Movie movie) {
        movieList.remove(movie);
        movie.setAdmin(null);
    }


}
