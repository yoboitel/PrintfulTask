package com.yohan.printfultask.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.yohan.printfultask.R;
import com.yohan.printfultask.util.Constants;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {

    private TextView movieTitle, movieVote, movieVoteCount, movieReleaseDate, movieOverview;
    private ImageView movieBackdropPoster, moviePoster, ivBackArrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Initialization
        initViews();

        //Retrieve the bundle from the previous activity
        Bundle b = getIntent().getExtras();
        if (b != null) {
            fillBundleDataIntoViews(b);
        }

        //Handle back arrow click
        ivBackArrow.setOnClickListener(view -> onBackPressed());
    }

    private void fillBundleDataIntoViews(Bundle b) {
        //Set the movie's poster
        Glide.with(getBaseContext())
                .load(Constants.BASE_URL_IMG + b.getString(Constants.MOVIE_POSTER_PATH))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(moviePoster);

        //Set the movie's backdrop poster
        Glide.with(getBaseContext())
                .load(Constants.BASE_URL_IMG_BACKGROUND + b.getString(Constants.MOVIE_BACKDROP_PATH))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(movieBackdropPoster);

        //Set the movie's title
        movieTitle.setSelected(true);
        movieTitle.setText(b.getString(Constants.MOVIE_TITLE));

        //Set the movie's vote rate
        DecimalFormat format = new DecimalFormat("0.#");
        movieVote.setText(String.format("%s%%", format.format(b.getDouble(Constants.MOVIE_VOTE) * 10)));

        //Set the movie's vote count
        movieVoteCount.setText(getString(R.string.from) + " " + b.getInt(Constants.MOVIE_VOTE_COUNT) + " " + getString(R.string.voters));

        //Set the movie's synopsis
        movieOverview.setText(b.getString(Constants.MOVIE_OVERVIEW));

        //Set the movie's release date
        movieReleaseDate.setText(b.getString(Constants.MOVIE_RELEASE_DATE));
    }

    private void initViews() {
        movieBackdropPoster = findViewById(R.id.backdrop_poster);
        moviePoster = findViewById(R.id.moviePoster);
        movieTitle = findViewById(R.id.tvDetailTitle);
        movieVote = findViewById(R.id.tvDetailVote);
        movieVoteCount = findViewById(R.id.tvDetailVoteCount);
        movieOverview = findViewById(R.id.tvDetailSynopsis);
        movieReleaseDate = findViewById(R.id.tvDetailDate);
        ivBackArrow = findViewById(R.id.ivBackArrow);
    }
}

