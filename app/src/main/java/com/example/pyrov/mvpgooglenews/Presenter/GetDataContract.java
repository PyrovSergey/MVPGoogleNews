package com.example.pyrov.mvpgooglenews.Presenter;

import com.example.pyrov.mvpgooglenews.Model.ArticlesItem;

import java.util.List;

public interface GetDataContract {
    interface View {
        void onGetDataSuccess(String message, List<ArticlesItem> list);

        void onGetDataFailure(String message);

        void setSwipeRefreshing(Boolean refreshing);
    }

    interface Presenter {
        void getDataFromURL(String query);
    }

    interface Data {
        void initRetrofitCall(String url);

    }

    interface onGetDataListener {
        void onSuccess(String message, List<ArticlesItem> list);

        void onFailure(String message);
    }
}
