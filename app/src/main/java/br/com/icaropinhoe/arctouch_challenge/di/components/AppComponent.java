package br.com.icaropinhoe.arctouch_challenge.di.components;

import javax.inject.Singleton;

import br.com.icaropinhoe.arctouch_challenge.di.modules.AppModule;
import br.com.icaropinhoe.arctouch_challenge.ui.detail.MovieActivity;
import br.com.icaropinhoe.arctouch_challenge.ui.movieList.MovieListActivity;
import dagger.Component;

/**
 * Created by icaro on 27/12/2017.
 */

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(MovieListActivity movieListActivity);

    void inject(MovieActivity movieActivity);
}
