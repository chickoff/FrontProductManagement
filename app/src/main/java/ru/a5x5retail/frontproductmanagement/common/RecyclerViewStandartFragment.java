package ru.a5x5retail.frontproductmanagement.common;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.a5x5retail.frontproductmanagement.ItemsRecyclerViewDecoration;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.base.BaseFragment;
import ru.a5x5retail.frontproductmanagement.db.models.InventoryList;
import ru.a5x5retail.frontproductmanagement.inventories.presenters.InventoryStatementPresenter;
import ru.a5x5retail.frontproductmanagement.inventories.view.IInventoryStatementView;
import ru.a5x5retail.frontproductmanagement.packinglistitems.PackingListItemsActivity;

public abstract class RecyclerViewStandartFragment<T> extends BaseFragment {

    public RecyclerViewStandartFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recycler_view_standart, container, false);
        initUi(view);
        setHasOptionsMenu(true);
        return view;
    }


    private SwipeRefreshLayout swipeRefreshLayout;
    protected BasicRecyclerViewAdapter<T> adapter;
    protected FloatingActionButton fab;

    private void initUi(View view ) {
        BasicViewHolderFactory viewHolderFactory = getViewHolderFactory();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new BasicRecyclerViewAdapter<>();
        adapter.setHolderFactory(viewHolderFactory);
        adapter.setLayout(viewHolderFactory.getItemLayoutId());
        adapter.setShortClickListener(new IRecyclerViewItemShortClickListener<T>() {
            @Override
            public void OnShortClick(int pos, View view, T innerItem) {
                onShortClick(pos,view,innerItem);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemsRecyclerViewDecoration());

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onDataRefresh(swipeRefreshLayout);
            }
        });
    }

    protected abstract void onShortClick(int pos, View view, T innerItem) ;

    protected abstract BasicViewHolderFactory getViewHolderFactory();

    protected abstract void onDataRefresh( SwipeRefreshLayout swipeRefreshLayout);

}
