package ru.a5x5retail.frontproductmanagement.checkinglistinc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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
        init();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checking_list_inc_position, container, false);
        initUi(view);
        return view;
    }

    private void init() {

    }

    private RecyclerView recyclerView;

    private void initUi (View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
       // recyclerView.
    }

    private void initViewModel() {

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
