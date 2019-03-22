package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.dlgfragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import ru.a5x5retail.frontproductmanagement.R;

public class InvoiceSwitchDialogFragment extends DialogFragment {


    private TextView mTitleTextView;
    private Button dlg_create_check_list_btn;
    private TextView dlg_cancel;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_invoice_switch, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        init(view);
        initUi();
        return view;
    }


    InvoiceSwithDialogFragmentResult resultListener;
    public InvoiceSwithDialogFragmentResult getResultListener() {
        return resultListener;
    }

    public void setResultListener(InvoiceSwithDialogFragmentResult resultListener) {
        this.resultListener = resultListener;
    }

    private void init(View view) {

        mTitleTextView = view.findViewById(R.id.mTitleTextView);
        dlg_create_check_list_btn = view.findViewById(R.id.dlg_create_check_list_btn);
        dlg_create_check_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resultListener != null) {
                    resultListener.createCheckList();
                    dismiss();
                }
            }
        });

        dlg_cancel = view.findViewById(R.id.dlg_cancel);
        dlg_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resultListener != null) {
                    resultListener.cancelDlg();
                    dismiss();
                }
            }
        });
    }

    private void initUi() {
        mTitleTextView.setText("Накладная номер: " + mTitle);
    }

    private String mTitle;
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;

    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }


    public interface InvoiceSwithDialogFragmentResult {
        void createCheckList();
        void cancelDlg();
    }

}
