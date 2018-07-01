package com.alohagoha.cityinfo;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class CoatOfArmsFragment extends Fragment {
    public static CoatOfArmsFragment create(int index) {
        CoatOfArmsFragment f = new CoatOfArmsFragment();

        Bundle args = new Bundle();
        args.putInt("index",index);
        f.setArguments(args);
        return f;
    }

    public int getIndex() {
        int index = getArguments().getInt("index",0);
        return index;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImageView coatOfArms = new ImageView(getActivity());

        TypedArray imgs = getResources().obtainTypedArray(R.array.coatofarms_imgs);

        coatOfArms.setImageResource(imgs.getResourceId(getIndex(), -1));
        return coatOfArms;
    }
}
