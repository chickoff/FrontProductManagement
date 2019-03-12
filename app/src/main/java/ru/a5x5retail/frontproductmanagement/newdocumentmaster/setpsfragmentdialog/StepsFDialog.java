package ru.a5x5retail.frontproductmanagement.newdocumentmaster.setpsfragmentdialog;

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

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.db.models.InvoiceHead;
import ru.a5x5retail.frontproductmanagement.interfaces.*;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.invoicemasters.InvoiceMasterViewModel;

public class StepsFDialog extends DialogFragment {



    private String mTitle;
    private Button dlg_cancel_btn;
    private IRecyclerViewItemClick<InvoiceHead> mListener;
    private InvoiceMasterViewModel viewModel;


    public StepsFDialog() {
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setViewModel(InvoiceMasterViewModel viewModel) {
        this.viewModel = viewModel;
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
                dismiss();
            }
        });


        RecyclerView rv;
        rv = view.findViewById(R.id.master_rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        StepsFDialogViewHolderFactory hf = new StepsFDialogViewHolderFactory();
        BasicRecyclerViewAdapter<InvoiceHead> adapter = new BasicRecyclerViewAdapter<>();
        adapter.setShortClickListener(new BasicRecyclerViewAdapter.IRecyclerViewItemShortClickListener<InvoiceHead>() {
            @Override
            public void OnShortClick(int pos, InvoiceHead innerItem) {
                viewModel.setSelectedInvoiceHead(innerItem);
                if (mListener != null) {
                    mListener.OnClick(pos, innerItem);
                }
            }
        });
        adapter.setLayout(R.layout.item_steps_f_dialog);
        adapter.setHolderFactory(hf);
        adapter.setSourceList(viewModel.getInvoiceHeadList());
        rv.setAdapter(adapter);
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
            mListener = (IRecyclerViewItemClick<InvoiceHead>) context;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }


    public class StepsFDialogViewHolder extends BasicViewHolder<InvoiceHead> {
        public StepsFDialogViewHolder(@NonNull View itemView) {
            super(itemView);
            i_steps_f_dialog_textview_1 = itemView.findViewById(R.id.i_steps_f_dialog_textview_1);
            i_steps_f_dialog_textview_2 = itemView.findViewById(R.id.i_steps_f_dialog_textview_2);
            i_steps_f_dialog_textview_3 = itemView.findViewById(R.id.i_steps_f_dialog_textview_3);
            i_steps_f_dialog_textview_4 = itemView.findViewById(R.id.i_steps_f_dialog_textview_4);
        }

        private TextView i_steps_f_dialog_textview_1;
        private TextView i_steps_f_dialog_textview_2;
        private TextView i_steps_f_dialog_textview_3;
        private TextView i_steps_f_dialog_textview_4;

        @Override
        public void setSource(InvoiceHead source) {
          //  i_steps_f_dialog_textview_1.setText(source.conractorNameLong);
            i_steps_f_dialog_textview_2.setText(source.dateDoc.toString());
            i_steps_f_dialog_textview_3.setText(source.summ);
            i_steps_f_dialog_textview_4.setText(source.numDoc);

        }
    }

    public class StepsFDialogViewHolderFactory extends BasicViewHolderFactory {
        @Override
        public BasicViewHolder getNewInstance(View itemView) {
            return new StepsFDialogViewHolder(itemView);
        }
    }

}
