package ru.a5x5retail.frontproductmanagement.checkinglistinc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.TestFragment;


public class CheckingListMarksFragment extends TestFragment {


    public CheckingListMarksFragment() {
        // Required empty public constructor
    }


    public static CheckingListMarksFragment newInstance() {
        CheckingListMarksFragment fragment = new CheckingListMarksFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_checking_list_marks, container, false);
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
