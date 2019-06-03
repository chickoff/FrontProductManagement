package ru.a5x5retail.frontproductmanagement.checkinglistinc.fragments;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.base.BaseFragment;

import ru.a5x5retail.frontproductmanagement.checkinglistinc.holders.MarksViewHolder;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListMark;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;


public class CheckingListMarksFragment extends BaseFragment implements ICheckingListIncView {

    @InjectPresenter(type = PresenterType.WEAK, tag = CheckingListIncPresenter.TAG)
    CheckingListIncPresenter presenter;

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


    @Override
    public void updateUi() {
        adapter.setSourceList(presenter.checkingListMarkList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void openEditableDialog(CheckingListPosition position) {

    }

    @Override
    public void openSelectiblePositionDialog(List<CheckingListPosition> checkingListPositionList) {

    }

}
