package com.yohan.printfultask.activities.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.yohan.printfultask.R;
import com.yohan.printfultask.activities.DetailActivity;
import com.yohan.printfultask.model.Movie;
import com.yohan.printfultask.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Movie> movieArrayList;

    public MovieAdapter() {
        movieArrayList = new ArrayList<>();
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new MoviesViewHolder(inflater.inflate(R.layout.movie_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final Movie movie = movieArrayList.get(position);

        holder.itemView.setOnClickListener(view -> {

            //Start DetailActivity when clicking a movie row.
            sendDataInBundleAndStartDetailActivity(view, movie);

        });
        //Set the movie's title
        final MoviesViewHolder moviesViewHolder = (MoviesViewHolder) holder;
        moviesViewHolder.tvMovieTitle.setText(movie.getTitle());
        //Allow the movies's title text to automatically scroll horizontally if too long.
        moviesViewHolder.tvMovieTitle.setSelected(true);
        //Set the movie's rating
        DecimalFormat format = new DecimalFormat("0.#");
        moviesViewHolder.tvMovieVote.setText(String.format("%s%%", format.format(movie.getVoteAverage() * 10)));
        //Set the movie's poster
        Glide.with(moviesViewHolder.ivMoviePoster.getContext())
                .load(Constants.BASE_URL_IMG + movie.getPosterPath())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(25)))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(moviesViewHolder.ivMoviePoster);
    }

    private void sendDataInBundleAndStartDetailActivity(View view, Movie movie) {
        Context context = view.getContext();

        Intent intent = new Intent(context, DetailActivity.class);
        Bundle b = new Bundle();

        b.putString(Constants.MOVIE_TITLE, movie.getTitle());
        b.putString(Constants.MOVIE_POSTER_PATH, movie.getPosterPath());
        b.putString(Constants.MOVIE_BACKDROP_PATH, movie.getBackdropPath());
        b.putDouble(Constants.MOVIE_VOTE, movie.getVoteAverage());
        b.putInt(Constants.MOVIE_VOTE_COUNT, movie.getVoteCount());
        b.putString(Constants.MOVIE_RELEASE_DATE, movie.getReleaseDate());
        b.putString(Constants.MOVIE_OVERVIEW, movie.getOverview());

        intent.putExtras(b);
        context.startActivity(intent);
    }

    public void addAll(List<Movie> moveResults) {
        for (Movie result : moveResults) {
            movieArrayList.add(result);
            notifyItemInserted(movieArrayList.size() - 1);
        }
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    protected static class MoviesViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvMovieTitle;
        private final TextView tvMovieVote;
        private final ImageView ivMoviePoster;

        public MoviesViewHolder(View itemView) {
            super(itemView);

            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            ivMoviePoster = itemView.findViewById(R.id.ivMoviePosterShimmer);
            tvMovieVote = itemView.findViewById(R.id.tvMovieVote);
        }
    }
}