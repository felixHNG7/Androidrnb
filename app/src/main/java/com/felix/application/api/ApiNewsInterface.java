package com.felix.application.api;

import com.felix.application.models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiNewsInterface {

    @GET("everything")
    Call<News> getNews(
        @Query("q") String q,
        @Query("apiKey") String apiKey
    );
}
