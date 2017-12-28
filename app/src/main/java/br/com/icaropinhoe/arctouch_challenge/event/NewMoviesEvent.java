package br.com.icaropinhoe.arctouch_challenge.event;

import java.util.List;

import br.com.icaropinhoe.arctouch_challenge.domain.Movie;

/**
 * Created by icaro on 27/12/2017.
 */

public class NewMoviesEvent {

    List<Movie> mMovies;

    public List<Movie> getMovies() {
        return mMovies;
    }

    public NewMoviesEvent(List<Movie> movies) {
        this.mMovies = movies;
    }

}
