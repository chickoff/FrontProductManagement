package ru.a5x5retail.frontproductmanagement.dialogs.standart;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.ItemsRecyclerViewDecoration;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.db.models.InvoiceHead;
import ru.a5x5retail.frontproductmanagement.interfaces.IRecyclerViewItemClick;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.invoicemasters.InvoiceMasterViewModel;

public class StandartFragmentDialog<T> extends DialogFragment {



    private String mTitle;
    private Button dlg_cancel_btn;
    private IRecyclerViewItemClick<T> mListener;
    private List<T> sourceList;
    private BasicViewHolderFactory viewHolderFactory;


    public StandartFragmentDialog() {
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setSourceList(List<T> sourceList) {
        this.sourceList = sourceList;
    }

    public void setViewHolderFactory(BasicViewHolderFactory viewHolderFactory) {
        this.viewHolderFactory = viewHolderFactory;
    }

    public void setRecyclerViewClickListener(IRecyclerViewItemClick<T> listener) {
        this.mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_steps_fdialog, container);
        init(view);
        getDialog().setTitle(mTitle);
        return view;
    }

    private void init(View view){
        dlg_cancel_btn = view.findViewById(R.id.dlg_cancel_btn);
        dlg_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.OnCancel();
                }
                dismiss();
            }
        });

        RecyclerView rv;
        rv = view.findViewById(R.id.master_rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        BasicRecyclerViewAdapter<T> adapter = new BasicRecyclerViewAdapter<>();
        adapter.setShortClickListener(new IRecyclerViewItemShortClickListener<T>() {
            @Override
            public void OnShortClick(int pos, View view, T innerItem) {
                if (mListener != null) {
                    mListener.OnClick(pos, innerItem);
                }
                dismiss();
            }
        });

        adapter.setLayout(viewHolderFactory.getItemLayoutId());
        adapter.setHolderFactory(viewHolderFactory);
        adapter.setSourceList(sourceList);
        rv.setAdapter(adapter);
        rv.addItemDecoration(new ItemsRecyclerViewDecoration());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attachListener(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        attachListener(activity);
    }

    private void attachListener(Context context){
        if (context instanceof IRecyclerViewItemClick){
            mListener = (IRecyclerViewItemClick<T>) context;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }
}
