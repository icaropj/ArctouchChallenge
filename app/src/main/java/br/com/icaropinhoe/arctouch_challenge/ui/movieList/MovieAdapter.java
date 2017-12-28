package br.com.icaropinhoe.arctouch_challenge.ui.movieList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.icaropinhoe.arctouch_challenge.R;
import br.com.icaropinhoe.arctouch_challenge.base.BaseAdapter;
import br.com.icaropinhoe.arctouch_challenge.domain.Movie;
import br.com.icaropinhoe.arctouch_challenge.util.Constants;
import br.com.icaropinhoe.arctouch_challenge.util.DateUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by icaro on 27/12/2017.
 */

public class MovieAdapter extends BaseAdapter<Movie> implements Filterable{

    private Context mContext;
    private MovieClickListener mListener;
    private List<Movie> dataListFiltered = new ArrayList<>();

    public MovieAdapter(Context context, MovieClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    public void addMovies(List<Movie> movies) {
        if(this.dataList == null) this.dataList = new ArrayList<>();
        this.dataList.addAll(movies);
        this.dataListFiltered.addAll(movies);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataListFiltered != null ? dataListFiltered.size() : 0;
    }

    @Override
    public Movie get(int position) {
        return dataListFiltered.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
        MovieViewHolder holder = new MovieViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie movie = dataListFiltered.get(position);

        final MovieViewHolder myHolder = (MovieViewHolder) holder;

        myHolder.tvName.setText(movie.getTitle());
        myHolder.tvReleaseDate.setText(DateUtil.dateToString(movie.getReleaseDate(), "dd/MM/yyyy"));

        myHolder.progress.setVisibility(View.VISIBLE);
        Picasso.with(mContext)
                .load(Constants.SMALL_IMAGE_PATH + movie.getPosterPath())
                .into(myHolder.ivPoster, new Callback() {
                    @Override
                    public void onSuccess() {
                        myHolder.progress.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        myHolder.progress.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    dataListFiltered = dataList;
                } else {
                    List<Movie> filteredList = new ArrayList<>();
                    for (Movie row : dataList) {

                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    dataListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataListFiltered = (ArrayList<Movie>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.movie_poster)
        ImageView ivPoster;

        @BindView(R.id.movie_name)
        TextView tvName;

        @BindView(R.id.movie_release_date)
        TextView tvReleaseDate;

        @BindView(R.id.imgProgress)
        ProgressBar progress;

        MovieViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null) mListener.onMovieClick(dataListFiltered.get(getAdapterPosition()));
        }
    }

    public interface MovieClickListener{
        void onMovieClick(Movie movie);
    }
}
