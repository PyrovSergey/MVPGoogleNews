package com.example.pyrov.mvpgooglenews.Presenter;

import android.text.TextUtils;

import com.example.pyrov.mvpgooglenews.Model.App;
import com.example.pyrov.mvpgooglenews.Model.ArticlesItem;
import com.example.pyrov.mvpgooglenews.Model.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Data implements GetDataContract.Data {
    private static final String KEY = "1d48cf2bd8034be59054969db665e62e";
    private static final String PUBLISHED_AT = "publishedAt";
    private static final String RU = "ru";
    private static final String SUCCESS = "Success";
    private static List<ArticlesItem> itemList;

    private GetDataContract.onGetDataListener listener;

    public Data(GetDataContract.onGetDataListener listener) {
        this.listener = listener;
        itemList = new ArrayList<>();
    }

    @Override
    public void initRetrofitCall(String query) {
        if (TextUtils.isEmpty(query)) {
            App.getGoogleApi().getData(RU, KEY).enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    itemList.clear();
                    itemList = response.body().getArticles();
                    listener.onSuccess(SUCCESS, itemList);
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                    listener.onFailure(t.getMessage());
                }
            });
        } else {
            App.getGoogleApi().getRequest(query, PUBLISHED_AT, 100, KEY).enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    itemList.clear();
                    itemList = response.body().getArticles();
                    listener.onSuccess(SUCCESS, itemList);
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                    listener.onFailure(t.getMessage());
                }
            });
        }
    }
}
