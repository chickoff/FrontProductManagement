package ru.a5x5retail.frontproductmanagement.inventories.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import org.w3c.dom.Text;

import ru.a5x5retail.frontproductmanagement.MainActivity;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.SuperPwdDialogFragment;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.common.RecyclerViewStandartFragment;
import ru.a5x5retail.frontproductmanagement.db.models.CheckListInventory;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db_local.ProjectMap;
import ru.a5x5retail.frontproductmanagement.filters.actualassortmentfilter.IActualAssortmentFilterCallListener;
import ru.a5x5retail.frontproductmanagement.inventories.dialogs.InventoryNoteDialogFragment;
import ru.a5x5retail.frontproductmanagement.inventories.presenters.InventorySheetsPresenter;
import ru.a5x5retail.frontproductmanagement.inventories.view.IInventorySheetsView;


public class InventorySheetsFragment extends RecyclerViewStandartFragment<CheckListInventory> implements IInventorySheetsView {

    public InventorySheetsFragment() {
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        fab = view.findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFabClick();
            }
        });
        return view;
    }

    @Override
    protected void onShortClick(int pos, View view, CheckListInventory innerItem) {
        ProjectMap.setInventoryCheckList(innerItem);
        presenter.setSelectedInventorySheet(innerItem);
        if (listener != null ) {
            listener.onSelect();
        }
    }


    @InjectPresenter
    InventorySheetsPresenter presenter;

    // TODO: Rename and change types and number of parameters
    public static InventorySheetsFragment newInstance(IInventorySheetsSelectedListener listener) {
        InventorySheetsFragment fragment = new InventorySheetsFragment();
        fragment.listener = listener;
        return fragment;
    }

    private void onFabClick() {
        showNoteDialog();
    }

    private void showNoteDialog() {
        final InventoryNoteDialogFragment dlg = new InventoryNoteDialogFragment();
        dlg.setListener(new InventoryNoteDialogFragment.IDialogActionListener() {
            @Override
            public void onOk(String note) {
                presenter.createCheckList(note);
            }

            @Override
            public void onCancel() {
                dlg.dismiss();
            }
        });
        FragmentManager fm = getFragmentManager();
        dlg.show(fm,"ghh");
    }

    @Override
    protected BasicViewHolderFactory getViewHolderFactory() {
        return new InventorySheetViewHolderFactory();
    }

    @Override
    protected void onDataRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setRefreshing(false);
    }


    IEditInventoryGoodsListener mListener;
    public IEditInventoryGoodsListener getmListener() {
        return mListener;
    }
    public void setmListener(IEditInventoryGoodsListener mListener) {
        this.mListener = mListener;
    }


    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity instanceof IEditInventoryGoodsListener) {
            mListener = (IEditInventoryGoodsListener) activity;
        }
    }

    @Override
    public void updateUi() {
        adapter.setSourceList(presenter.getCheckingListHeadList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setEditInventoryGoodsFragment() {
        if (mListener !=null ) {
            mListener.onEditInventoryGoods();
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_inventory_sheets, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.edit_inventory :
                FragmentActivity fa =  getActivity();
                SuperPwdDialogFragment dialog = new SuperPwdDialogFragment();
                dialog.setResult(new SuperPwdDialogFragment.SuperPwdDialogFragmentResult() {
                    @Override
                    public void onSuperPwdSuccess() {
                        presenter.setEditInventoryGoodsFragment();
                    }
                });
                dialog.show(fa.getSupportFragmentManager(),"dsfds");
            break;
        }

        return super.onOptionsItemSelected(item);
    }



    /*********************************************************************************************/

    public class InventorySheetViewHolder extends BasicViewHolder<CheckListInventory> {

        TextView text_view_1,text_view_2;

        public InventorySheetViewHolder(@NonNull View itemView) {
            super(itemView);
            text_view_1 = itemView.findViewById(R.id.text_view_1);
            text_view_2 = itemView.findViewById(R.id.text_view_2);
            /*text_view_3 = itemView.findViewById(R.id.text_view_3);
            text_view_4 = itemView.findViewById(R.id.text_view_4);
            text_view_5 = itemView.findViewById(R.id.text_view_5);
        }*/

          //, text_view_2, text_view_3, text_view_4, text_view_5;



        }

        @Override
        public void setSource(CheckListInventory source) {
            text_view_1.setText(source.nameDoc);
            text_view_2.setText(source.note);
        }
    }

    public class InventorySheetViewHolderFactory extends BasicViewHolderFactory {
        public InventorySheetViewHolderFactory() {
            setItemLayoutId(R.layout.item_inventory_sheet);
        }

        @Override
        public RecyclerView.ViewHolder getNewInstance(View itemView) {
            return new InventorySheetViewHolder(itemView);
        }
    }


    private IInventorySheetsSelectedListener listener;
    public void setListener(IInventorySheetsSelectedListener listener) {
        this.listener = listener;
    }

    public interface IInventorySheetsSelectedListener {
        void onSelect();
    }

    public interface IEditInventoryGoodsListener {
        void onEditInventoryGoods();
    }
}

