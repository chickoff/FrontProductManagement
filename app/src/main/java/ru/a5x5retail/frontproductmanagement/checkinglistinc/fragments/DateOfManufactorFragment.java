package ru.a5x5retail.frontproductmanagement.checkinglistinc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.TestFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DateOfManufactorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateOfManufactorFragment extends TestFragment {
    public DateOfManufactorFragment() {
        // Required empty public constructor
    }

    public static DateOfManufactorFragment newInstance() {
        DateOfManufactorFragment fragment = new DateOfManufactorFragment();
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
        return inflater.inflate(R.layout.fragment_date_of_manufactor, container, false);
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
