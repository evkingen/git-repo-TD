package com.alohagoha.weather1a.data;

import android.widget.TextView;

public interface IDataManager {
    void initRetrofit();

    void requestRetrofit(final TextView textView, String city, String keyApi);
}