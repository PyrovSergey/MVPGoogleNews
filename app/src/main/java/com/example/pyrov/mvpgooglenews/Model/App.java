package com.example.pyrov.mvpgooglenews.Model;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    public static final String BASE_URL = "https://newsapi.org";
    private static GoogleApi googleApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        googleApi = retrofit.create(GoogleApi.class);
    }

    public static GoogleApi getGoogleApi() {
        return googleApi;
    }
}
