package com.alohagoha.cityinfo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CitiesFragment extends ListFragment
{
    boolean isExistCoatoframs;
    int currentPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Cities,android.R.layout.simple_list_item_activated_1);
        setListAdapter(adapter);

        View detailsFrame = getActivity().findViewById(R.id.coat_of_arms);

        isExistCoatoframs = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;


        if(savedInstanceState!=null) {
            currentPosition = savedInstanceState.getInt("CurrentCity",0);
        }

        if(isExistCoatoframs) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showCoatOfArms();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CurrentCity",currentPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        currentPosition = position;
        showCoatOfArms();
    }

    private void showCoatOfArms() {
        if(isExistCoatoframs)
            getListView().setItemChecked(currentPosition,true);

            CoatOfArmsFragment detail = (CoatOfArmsFragment)getFragmentManager().findFragmentById(R.id.coat_of_arms);
            if(detail==null || detail.getIndex() != currentPosition) {
                detail = CoatOfArmsFragment.create(currentPosition);
                FragmentTransaction ft =(FragmentTransaction) getFragmentManager().beginTransaction();
                ft.replace(R.id.coat_of_arms,detail);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

    }


}
