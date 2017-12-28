package br.com.icaropinhoe.arctouch_challenge.event;

import br.com.icaropinhoe.arctouch_challenge.domain.Movie;

/**
 * Created by icaro on 27/12/2017.
 */

public class DetailFetchedEvent {

    Movie mMovie;

    public Movie getMovie() {
        return mMovie;
    }

    public DetailFetchedEvent(Movie movie) {
        this.mMovie = movie;
    }
}
