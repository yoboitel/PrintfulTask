package com.yohan.printfultask.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.yohan.printfultask.R;
import com.yohan.printfultask.activities.adapters.MovieAdapter;
import com.yohan.printfultask.api.ApiClient;
import com.yohan.printfultask.api.ApiHelper;
import com.yohan.printfultask.model.MoviesResult;
import com.yohan.printfultask.util.Constants;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.schwaab.avvylib.AvatarView;

public class MainActivity extends AppCompatActivity {

    private ShimmerFrameLayout shimmerFrameLayout;
    private AvatarView avatarView;
    private MovieAdapter adapter;
    private RecyclerView rv;
    private ApiHelper apiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialization
        initViews();

        //Open story video when click the instagram-like avatar
        handleAvatarInstagramClick();

        //RecyclerView and adapter setup
        adapter = new MovieAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);

        //Init service and load data
        apiHelper = ApiClient.getClient().create(ApiHelper.class);
        loadMovies();
    }

    private void handleAvatarInstagramClick() {
        avatarView.setImageDrawable(ResourcesCompat.getDrawable(getBaseContext().getResources(), R.drawable.pp, null));
        avatarView.setOnClickListener(view -> {
            avatarView.setAnimating(true);
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                avatarView.setAnimating(false);
                //start Video Activity
                Intent i = new Intent(getBaseContext(), VideoActivity.class);
                startActivity(i);
            }, 1500);
        });
    }

    private void initViews() {
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        avatarView = findViewById(R.id.avatarView);
        rv = findViewById(R.id.main_recycler);
    }

    private void loadMovies() {

        Call<MoviesResult> request = apiHelper.getPlayingNowMovies(Constants.API_KEY, "en_US");
        request.enqueue(new Callback<MoviesResult>() {
            @Override
            public void onResponse(@NotNull Call<MoviesResult> call, @NotNull Response<MoviesResult> response) {

                //Fill the movies list from the response in the adapter
                if (response.body() != null)
                    adapter.addAll(response.body().getMovies());

                //Movies data is retrieved so we stop the shimmer_row effect
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NotNull Call<MoviesResult> call, @NotNull Throwable t) {

                shimmerFrameLayout.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });
    }

    //Start shimmer_row effect onResume
    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    //Stop shimmer_row effect onPause
    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }
}