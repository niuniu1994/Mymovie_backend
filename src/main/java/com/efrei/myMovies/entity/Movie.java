package com.efrei.myMovies.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private int movieId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String subtitle;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private String actors;

    @Column(name = "min_age", nullable = false)
    private int minAge;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Session> sessionList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id")
    private CinemaAdmin admin;

    public void addSession(Session session) {
        session.setMovie(this);
        sessionList.add(session);
    }

    public void addSessions(List<Session> sessions){
        sessions.forEach(session -> session.setMovie(this));
        sessionList.addAll(sessions);
    }

    public void removeSession(Session session) {
        sessionList.remove(session);
        session.setMovie(null);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", language='" + language + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", director='" + director + '\'' +
                ", actors='" + actors + '\'' +
                ", minAge=" + minAge +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", sessionSet size=" + sessionList.size() +
                ", admin=" + admin +
                '}';
    }
}
