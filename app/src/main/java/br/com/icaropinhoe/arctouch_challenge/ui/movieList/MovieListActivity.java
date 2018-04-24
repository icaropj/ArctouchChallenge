package br.com.icaropinhoe.arctouch_challenge.ui.movieList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import br.com.icaropinhoe.arctouch_challenge.R;
import br.com.icaropinhoe.arctouch_challenge.base.BaseActivity;
import br.com.icaropinhoe.arctouch_challenge.di.DaggerInjector;
import br.com.icaropinhoe.arctouch_challenge.domain.Movie;
import br.com.icaropinhoe.arctouch_challenge.event.DetailFetchedEvent;
import br.com.icaropinhoe.arctouch_challenge.event.ErrorEvent;
import br.com.icaropinhoe.arctouch_challenge.event.NewMoviesEvent;
import br.com.icaropinhoe.arctouch_challenge.ui.detail.MovieActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListActivity extends BaseActivity implements MovieListContract.View, MovieAdapter.MovieClickListener {

    private MovieAdapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout mSwipeContainer;

    @Inject
    MovieListPresenter mPresenter;

    private SearchView mSearchView;

    private boolean isLoadingMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setUp();
    }

    private void setUp() {
        DaggerInjector.get().inject(this);
        initViews();
    }

    private void initViews() {
        initToolbar(mToolbar, getString(R.string.app_name));

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                if (!isLoadingMore && totalItemCount <= (lastVisible + 5)) {
                    isLoadingMore = true;
                    loadMore();
                }
            }
        });

        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchMovies();
            }
        });
        setAdapter(null);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipeContainer.setRefreshing(true);
        fetchMovies();
    }

    private void fetchMovies() {
        mPresenter.fetchMovies();
    }

    private void loadMore() {
        mPresenter.loadMore();
    }

    private void setAdapter(List<Movie> movies){
        mAdapter = new MovieAdapter(getContext(), this);
        if(movies != null) mAdapter.addMovies(movies);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onMovieClick(Movie movie) {
        onLoading(true);
        mPresenter.fetchMovieDetail(movie.getId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) item.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!mSearchView.isIconified()) {
            mSearchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NewMoviesEvent newMoviesEvent) {
        isLoadingMore = false;
        onLoading(false);
        mAdapter.addMovies(newMoviesEvent.getMovies());
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ErrorEvent errorEvent) {
        isLoadingMore = false;
        onLoading(false);
        onError("Couldn't fetch movies. Try again later...");
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DetailFetchedEvent event) {
        onLoading(false);
        Intent intent = new Intent(MovieListActivity.this, MovieActivity.class);
        intent.putExtra("movie", Parcels.wrap(event.getMovie()));
        startActivity(intent);
    }

    @Override
    public void onLoading(boolean isLoading) {
        mSwipeContainer.setRefreshing(isLoading);
    }

    @Override
    public void onError(String errorMsg) {
        Snackbar.make(mRecyclerView, errorMsg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
