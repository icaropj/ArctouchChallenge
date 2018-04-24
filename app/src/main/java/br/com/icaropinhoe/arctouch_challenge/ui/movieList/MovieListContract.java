package br.com.icaropinhoe.arctouch_challenge.ui.movieList;

import android.content.Context;

/**
 * Created by icaro on 27/12/2017.
 */

public interface MovieListContract {

    interface View{
        void onLoading(boolean isLoading);

        void onError(String errorMsg);

        Context getContext();
    }

    interface Presenter {
        void fetchMovies();
    }

}
