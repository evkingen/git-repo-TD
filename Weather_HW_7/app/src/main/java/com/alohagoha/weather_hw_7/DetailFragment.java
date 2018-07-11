package com.alohagoha.weather_hw_7;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailFragment extends BaseFragment {

    //объявление переменных
    private EditText editTextCountry;
    private RecyclerView recyclerView;
    CreateActionFragment.OnHeadlineSelectedListener mCallback;
    private LinearLayout linearLayout;
    private ArrayList<String> tempList;

    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(ArrayList<String> position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Toast.makeText(getContext(), "onAttachAction", Toast.LENGTH_SHORT).show();

        try {
            mCallback = (CreateActionFragment.OnHeadlineSelectedListener) getBaseActivity().getAnotherFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getBaseActivity().getAnotherFragment().toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //обращаемся к layout который будет содержать наш фрагмент
        return inflater.inflate(R.layout.detail_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {
        initCountryList();

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        CustomAdapter customAdapter = new CustomAdapter(getContext(), tempList, mCallback);
        recyclerView.setAdapter(customAdapter);




    }

    private void initCountryList() {
        tempList = new ArrayList<>();
        TypedArray temp = getResources().obtainTypedArray(R.array.detailtemp);
        int length = temp.length();
        String[] answer = new String[length];
        for(int i = 0 ; i < length ; i++) {
            tempList.add(temp.getString(i));
        }
    }
}

