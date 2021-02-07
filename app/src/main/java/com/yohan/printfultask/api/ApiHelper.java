package com.yohan.printfultask.api;

import com.yohan.printfultask.model.MoviesResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiHelper {

    @GET("/3/movie/now_playing")
    Call<MoviesResult> getPlayingNowMovies(@Query("api_key") String apiKey, @Query("language") String language);
}
