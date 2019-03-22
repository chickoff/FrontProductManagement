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

public class PlanIncomeSwitchDialogFragment extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_plan_income_switch, container);
        init(view);
        title.setText("П/П номер: " + mTitle);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }


    PlanIncomeSwithDialogFragmentResult resultListener;
    public PlanIncomeSwithDialogFragmentResult getResultListener() {
        return resultListener;
    }

    public void setResultListener(PlanIncomeSwithDialogFragmentResult resultListener) {
        this.resultListener = resultListener;
    }



    Button dlg_create_check_list_btn;
    TextView dlg_cancel,title;

    private void init(View view) {

        title = view.findViewById(R.id.tttt);

        dlg_create_check_list_btn = view.findViewById(R.id.dlg_create_check_list_btn);
        dlg_create_check_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resultListener != null) {
                    resultListener.createInvoice();
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

    private String mTitle;
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;

    }

    public interface InvoiceSwithDialogFragmentResult {
        void createCheckList();
        void cancelDlg();
    }


    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }

    public interface PlanIncomeSwithDialogFragmentResult {
        void createInvoice();
        void cancelDlg();
    }

}
