package com.example.pyrov.mvpgooglenews.View;

import android.net.Uri;

public interface ContractActivity {

    interface ViewActivity {
        void showToast(String message);

        void startBrowser(Uri url);
    }
}
