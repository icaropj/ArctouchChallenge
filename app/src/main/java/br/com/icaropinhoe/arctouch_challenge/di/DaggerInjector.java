package br.com.icaropinhoe.arctouch_challenge.di;


import br.com.icaropinhoe.arctouch_challenge.di.components.AppComponent;
import br.com.icaropinhoe.arctouch_challenge.di.components.DaggerAppComponent;
import br.com.icaropinhoe.arctouch_challenge.di.modules.AppModule;

/**
 * Created by icaro on 27/12/2017.
 */

public class DaggerInjector {

    private static AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();

    public static AppComponent get() {
        return appComponent;
    }

}
