package br.com.icaropinhoe.arctouch_challenge.data.remote;

import java.util.List;

import br.com.icaropinhoe.arctouch_challenge.domain.ApiResponse;
import br.com.icaropinhoe.arctouch_challenge.domain.Movie;
import br.com.icaropinhoe.arctouch_challenge.util.Constants;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by icaro on 27/12/2017.
 */

public interface MoviesApi {

    @GET(Constants.UPCOMING)
    public Call<ApiResponse> upcomingMovies(@Query("page") Integer page);

    @GET("{movieId}")
    public Call<Movie> getDetail(@Path("movieId") Integer movieId);

}
