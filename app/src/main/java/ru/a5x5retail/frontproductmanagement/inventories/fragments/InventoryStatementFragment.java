package ru.a5x5retail.frontproductmanagement.inventories.fragments;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.common.RecyclerViewStandartFragment;
import ru.a5x5retail.frontproductmanagement.conversions.Converter;
import ru.a5x5retail.frontproductmanagement.db.models.InventoryList;
import ru.a5x5retail.frontproductmanagement.inventories.presenters.InventoryStatementPresenter;
import ru.a5x5retail.frontproductmanagement.inventories.view.IInventoryStatementView;

public class InventoryStatementFragment extends RecyclerViewStandartFragment<InventoryList> implements IInventoryStatementView {

    @InjectPresenter
    InventoryStatementPresenter presenter;
    private IInventoryStatementSelectedListener listener;
    public void setListener(IInventoryStatementSelectedListener listener) {
        this.listener = listener;
    }

    public static InventoryStatementFragment newInstance(IInventoryStatementSelectedListener listener) {
        InventoryStatementFragment fragment = new InventoryStatementFragment();
        fragment.setListener(listener);
        return fragment;
    }

    @Override
    protected void onShortClick(int pos, View view, InventoryList innerItem) {
        presenter.setSelectedInventoryList(innerItem);
        if (listener != null) {
            listener.onSelect();
        }
    }

    @Override
    protected BasicViewHolderFactory getViewHolderFactory() {
        return new InventoryStatementViewHolderFactory();
    }

    @Override
    protected void onDataRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void updateUi() {
        adapter.setSourceList(presenter.getInventoryList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    public class InventoryStatementViewHolder extends BasicViewHolder<InventoryList> {

        public InventoryStatementViewHolder(@NonNull View itemView) {
            super(itemView);
            text_view_1 = itemView.findViewById(R.id.text_view_1);
            text_view_2 = itemView.findViewById(R.id.text_view_2);
            text_view_3 = itemView.findViewById(R.id.text_view_3);
            text_view_4 = itemView.findViewById(R.id.text_view_4);
            text_view_5 = itemView.findViewById(R.id.text_view_5);
        }

        TextView text_view_1, text_view_2, text_view_3, text_view_4, text_view_5;

         @Override
        public void setSource(InventoryList source) {
             text_view_1.setText(source.nameLong);
             Converter.Date4TextView(text_view_2,source.startDate);
             text_view_3.setText(source.inventoryTypeNameLong);
             text_view_4.setText(source.divisionPlacementNameLong);
             text_view_5.setText(source.inventoryStateNameLong);
        }
    }

    public class InventoryStatementViewHolderFactory extends BasicViewHolderFactory {
        public InventoryStatementViewHolderFactory() {
            setItemLayoutId(R.layout.item_inventory_statement);
        }

        @Override
        public RecyclerView.ViewHolder getNewInstance(View itemView) {
            return new InventoryStatementViewHolder(itemView);
        }
    }

    public interface IInventoryStatementSelectedListener {
        void onSelect();
    }
}
