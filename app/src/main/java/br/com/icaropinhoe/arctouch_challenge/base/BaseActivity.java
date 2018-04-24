package br.com.icaropinhoe.arctouch_challenge.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by icaro on 27/12/2017.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initToolbar(Toolbar toolBar, String title) {
        initToolbar(toolBar, title, false);
    }

    protected void initToolbar(Toolbar toolBar, String title, boolean displayHome) {
        if (toolBar != null) {
            setSupportActionBar(toolBar);
            ActionBar actionBar = getSupportActionBar();

            if (actionBar != null) {
                actionBar.setTitle(title);
                actionBar.setDisplayHomeAsUpEnabled(displayHome);
            }
        }
    }

}
