package com.example.pyrov.mvpgooglenews.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.pyrov.mvpgooglenews.Model.ArticlesItem;
import com.example.pyrov.mvpgooglenews.Presenter.GetDataContract;
import com.example.pyrov.mvpgooglenews.Presenter.Presenter;
import com.example.pyrov.mvpgooglenews.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ContractActivity.ViewActivity, GetDataContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.search_bar)
    SearchView searchBar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private Toast toast;
    private NewsAdapter adapter;
    private GetDataContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        presenter = new Presenter(this);
        presenter.getDataFromURL(null);
        swipe.setOnRefreshListener(this);
        searchBar.setIconifiedByDefault(true);
        searchBar.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                presenter.getDataFromURL(null);
                return false;
            }
        });

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.getDataFromURL(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    presenter.getDataFromURL(null);
                }
                return false;
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);
    }

    @Override
    public void startBrowser(Uri url) {
        startActivity(new Intent(Intent.ACTION_VIEW, url));
    }

    @Override
    public void onGetDataSuccess(String message, List<ArticlesItem> list) {
        adapter = new NewsAdapter(this, list);
        recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetDataFailure(String message) {
        showToast(message);
    }

    @Override
    public void onRefresh() {
        presenter.getDataFromURL("");
    }

    @Override
    public void setSwipeRefreshing(Boolean refreshing) {
        swipe.setRefreshing(refreshing);
    }


    @Override
    public void showToast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchBar.setIconifiedByDefault(true);
        searchBar.destroyDrawingCache();
    }
}
