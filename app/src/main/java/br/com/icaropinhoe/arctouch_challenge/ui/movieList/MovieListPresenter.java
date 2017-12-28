package br.com.icaropinhoe.arctouch_challenge.ui.movieList;

import android.support.annotation.NonNull;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import br.com.icaropinhoe.arctouch_challenge.data.remote.MoviesApi;
import br.com.icaropinhoe.arctouch_challenge.domain.ApiResponse;
import br.com.icaropinhoe.arctouch_challenge.domain.Movie;
import br.com.icaropinhoe.arctouch_challenge.event.DetailFetchedEvent;
import br.com.icaropinhoe.arctouch_challenge.event.ErrorEvent;
import br.com.icaropinhoe.arctouch_challenge.event.NewMoviesEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by icaro on 27/12/2017.
 */

public class MovieListPresenter implements MovieListContract.Presenter {

    private MoviesApi mMovesApi;

    private static int mCurrentPage = 1;

    @Inject
    public MovieListPresenter(MoviesApi moviesApi) {
        this.mMovesApi = moviesApi;
    }

    @Override
    public void fetchMovies() {
        Call<ApiResponse> moviesCall = mMovesApi.upcomingMovies(mCurrentPage);
        moviesCall.enqueue(new FetchMoviesCallback());
    }

    public void loadMore(){
        mCurrentPage++;
        fetchMovies();
    }

    public void fetchMovieDetail(Integer movieId) {
        Call<Movie> detailCall = mMovesApi.getDetail(movieId);
        detailCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(response.isSuccessful()){
                    EventBus.getDefault().post(new DetailFetchedEvent(response.body()));
                }else{
                    EventBus.getDefault().post(new ErrorEvent());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                EventBus.getDefault().post(new ErrorEvent());
            }
        });
    }

    class FetchMoviesCallback implements Callback<ApiResponse> {
        @Override
        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
            if(response.isSuccessful()){
                EventBus.getDefault().post(new NewMoviesEvent(response.body().getResults()));
            }else{
                EventBus.getDefault().post(new ErrorEvent());
            }
        }

        @Override
        public void onFailure(Call<ApiResponse> call, Throwable t) {
            EventBus.getDefault().post(new ErrorEvent());
        }
    }

}
