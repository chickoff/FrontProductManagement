package ru.a5x5retail.frontproductmanagement.inventories.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.UUID;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.BaseFragment;
import ru.a5x5retail.frontproductmanagement.inventories.presenters.InventoryPreviewPresenter;
import ru.a5x5retail.frontproductmanagement.inventories.view.IInventoryPreviewView;
import ru.a5x5retail.frontproductmanagement.сheckinglist.document.CheckingListGoodsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryPreviewFragment extends BaseFragment implements IInventoryPreviewView {


    public void setListener(IInventoryPreviewFragmentListener mListener) {
        this.mListener = mListener;
    }

    IInventoryPreviewFragmentListener mListener;

    public static InventoryPreviewFragment newInstance(IInventoryPreviewFragmentListener mListener) {
        InventoryPreviewFragment fragment = new InventoryPreviewFragment();
        fragment.setListener(mListener);
        return fragment;
    }

    public InventoryPreviewFragment() {
        // Required empty public constructor
    }

    @InjectPresenter
    InventoryPreviewPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_inventory_preview, container, false);
        initUi(view);
        return view;
    }

    private void initUi(View view) {
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (mListener != null) {
                   mListener.onSelect();
               }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.packing_list_preview_activity_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.a_pl_preview_upd_item_1 :
                presenter.doSyncInRr();
                break;
        }

        return true;
    }

    @Override
    public void startGoods() {
        Intent intent = new Intent(getActivity(), CheckingListGoodsActivity.class);
        presenter.getSelected();
        intent.putExtra("gggg", UUID.fromString(presenter.getSelected().checkingListHeadGuid));
        startActivity(intent);
    }

    public interface IInventoryPreviewFragmentListener {
        void onSelect();
    }

}
