package ru.a5x5retail.frontproductmanagement.checkinglistinc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.TestFragment;




public class CheckingListIncPositionFragment extends TestFragment {


    public CheckingListIncPositionFragment() {
        // Required empty public constructor
    }


    public static CheckingListIncPositionFragment newInstance() {
        CheckingListIncPositionFragment fragment = new CheckingListIncPositionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checking_list_inc_position, container, false);
    }


    @Override
    protected void viewModelDataIsChanged() {

    }

    @Override
    public void listenerChangedListenerRemove() {

    }

    @Override
    public void listenerChangedListenerAdded() {

    }
}
