package com.example.pyrov.mvpgooglenews.Presenter;

import com.example.pyrov.mvpgooglenews.Model.ArticlesItem;

import java.util.List;

public class Presenter implements GetDataContract.Presenter, GetDataContract.onGetDataListener {

    private GetDataContract.View view;
    private Data data;

    public Presenter(GetDataContract.View view) {
        this.view = view;
        data = new Data(this);
    }

    @Override
    public void getDataFromURL(String query) {
        view.setSwipeRefreshing(true);
        data.initRetrofitCall(query);
    }

    @Override
    public void onSuccess(String message, List<ArticlesItem> list) {
        view.onGetDataSuccess(message, list);
        view.setSwipeRefreshing(false);
    }

    @Override
    public void onFailure(String message) {
        view.onGetDataFailure(message);
        view.setSwipeRefreshing(false);
    }
}
