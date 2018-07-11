package com.alohagoha.socnet;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

public class DataSourceBuilder {

    private List<Soc> dataSource;
    private Resources resources;

    public DataSourceBuilder(Resources r) {
        dataSource = new ArrayList<>(11);
        this.resources = r;
    }

    public List<Soc> build() {
        String[] descriptions = resources.getStringArray(R.array.cities);
        int[] pictures = getImageArray();

        for(int i = 0 ; i < descriptions.length ; i++) {
            dataSource.add(new Soc(descriptions[i],pictures[i],false));
        }
        return dataSource;
    }

    private int[] getImageArray() {
        TypedArray pictures = resources.obtainTypedArray(R.array.pictures);
        int lenght = pictures.length();
        int[] answer = new int[lenght];
        for(int i = 0 ; i < lenght ; i++) {
            answer[i] = pictures.getResourceId(i,0);
        }
        return answer;
    }
}
