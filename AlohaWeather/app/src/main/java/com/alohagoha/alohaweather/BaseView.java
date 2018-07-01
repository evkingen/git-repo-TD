package com.alohagoha.alohaweather;

import android.graphics.Bitmap;

public interface BaseView {
    interface View {

        Boolean isNetworkAvailable();

        void initDrawer(String username, Bitmap profileImage);
    }

    interface Presenter<V> {

        void onAttach(V view);

        void onDetach();

        void onDetachView();
    }
}
