package br.com.icaropinhoe.arctouch_challenge.ui.detail;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import br.com.icaropinhoe.arctouch_challenge.R;
import br.com.icaropinhoe.arctouch_challenge.base.BaseActivity;
import br.com.icaropinhoe.arctouch_challenge.di.DaggerInjector;
import br.com.icaropinhoe.arctouch_challenge.domain.Genre;
import br.com.icaropinhoe.arctouch_challenge.domain.Movie;
import br.com.icaropinhoe.arctouch_challenge.util.Constants;
import br.com.icaropinhoe.arctouch_challenge.util.DateUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by icaro on 27/12/2017.
 */

public class MovieActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.movie_image)
    ImageView mIvPoster;

    @BindView(R.id.movie_name)
    TextView mTvName;

    @BindView(R.id.movie_release_date)
    TextView mTvReleaseDate;

    @BindView(R.id.movie_overview)
    TextView mTvOverview;

    @BindView(R.id.chips_container)
    LinearLayout mChipsContainer;

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        DaggerInjector.get().inject(this);

        if(getIntent() == null){
            finish();
        }

        this.mMovie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        initViews();
    }

    private void initViews() {
        initToolbar(mToolbar, this.mMovie.getTitle(), true);

        Picasso.with(MovieActivity.this)
                .load(Constants.LARGE_IMAGE_PATH + mMovie.getPosterPath())
                .resize(500, 500)
                .into(mIvPoster);

        mTvName.setText(mMovie.getTitle());
        mTvReleaseDate.setText(DateUtil.dateToString(mMovie.getReleaseDate(), "dd/MM/yyyy"));
        mTvOverview.setText(mMovie.getOverview());

        List<Genre> genres = mMovie.getGenres();
        for (Genre genre: genres) {
            createGenreChip(genre);
        }
    }

    private void createGenreChip(Genre genre) {
        TextView tvChip = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 6, 0);
        tvChip.setLayoutParams(params);
        tvChip.setText(genre.getName());
        tvChip.setTextColor(getResources().getColor(android.R.color.white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            tvChip.setBackground(getResources().getDrawable(R.drawable.simple_chip));
        }
        mChipsContainer.addView(tvChip);
    }

}
