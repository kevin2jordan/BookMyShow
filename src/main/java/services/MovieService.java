package services;

import exceptions.AlreadyExistException;
import exceptions.NotFoundException;
import lombok.NonNull;
import model.Movie;
import model.enums.Genre;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MovieService {

    private final Map<String, Movie> movies;

    public MovieService() {
        movies = new HashMap<>();
    }

    public Movie getMovieById(@NonNull final String movieId){
        if(!movies.containsKey(movieId)){
            throw new NotFoundException("Movie not found");
        }
        return movies.get(movieId);
    }

    public void addMovie(@NonNull final String movieName) {

        Movie newMovie = new Movie(UUID.randomUUID().toString(), movieName);
        movies.put(newMovie.getMovieId(), newMovie);
    }
}
