package com.example.pyrov.mvpgooglenews.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleApi {
    @GET("/v2/top-headlines")
    Call<News> getData(@Query("country") String resourceName, @Query("apiKey") String key);

    @GET("/v2/everything")
    Call<News> getRequest(@Query("q") String query, @Query("sortBy") String sortBy, @Query("pageSize") int pageSize, @Query("apiKey") String key);
}
