package br.com.icaropinhoe.arctouch_challenge.domain;

import java.util.List;

import br.com.icaropinhoe.arctouch_challenge.domain.Movie;

/**
 * Created by icaro on 27/12/2017.
 */

public class ApiResponse {

    private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
