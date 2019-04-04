package ru.a5x5retail.frontproductmanagement.checkinglistinc.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.base.TestFragment;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.CheckingListIncViewModel;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.holders.MarksViewHolder;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListMark;


public class CheckingListMarksFragment extends TestFragment<CheckingListIncViewModel> {


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

        View view = inflater.inflate(R.layout.fragment_checking_list_marks, container, false);
        initUi(view);
        initViewModel();
        return view;
    }

    private BasicRecyclerViewAdapter<CheckingListMark> adapter;
    private RecyclerView recyclerView;

    private void initUi(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new BasicRecyclerViewAdapter<>();
        adapter.setShortClickListener(new IRecyclerViewItemShortClickListener<CheckingListMark>() {
            @Override
            public void OnShortClick(int pos, View view, CheckingListMark innerItem) {

            }
        }).setLayout(R.layout.item_checking_list_marks)
                .setHolderFactory(new MarksViewHolder.MarksViewHolderFactory());


        recyclerView.setAdapter(adapter);
    }

    private void initViewModel() {
        FragmentActivity activity = getActivity();
        setViewModel( ViewModelProviders.of(activity).get(CheckingListIncViewModel.class));

    }

    private void updateUi() {
        if (getViewModel() == null) return;
        adapter.setSourceList(getViewModel().checkingListMarkList);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void viewModelDataIsChanged() {
        updateUi();
    }

    @Override
    public void listenerChangedListenerRemove() {

    }

    @Override
    public void listenerChangedListenerAdded() {
        updateUi();
    }


}
