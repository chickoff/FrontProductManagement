package ru.a5x5retail.frontproductmanagement.checkinglistinc.dialogs;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;
import ru.a5x5retail.frontproductmanagement.сheckinglist.Dialog.MyCustomDialog;


public class CheckingListPositionDialogFragment extends DialogFragment {

    private static final String TAG = "MyCustomDialog";

    // private CheckingListGoodsRvVievHolder.ITestClick click;

    //0-замещение вводимого значения, заполнение по умолчанию сумарное количество
    //1-инкремент  вводимого значения, заполнение по умолчанию 0
    //




    public CheckingListPositionDialogFragment() {

    }


    private CheckingListPosition position;
    public void setPosition(CheckingListPosition position) {
        this.position = position;
    }

    private INewQtyListener iNewQtyListener;
    public void setiNewQtyListener(INewQtyListener iNewQtyListener) {
        this.iNewQtyListener = iNewQtyListener;
    }

    private BigDecimal qtyFromBarcode;
    public void setQtyFromBarcode(BigDecimal qtyFromBarcode) {
        this.qtyFromBarcode = qtyFromBarcode;
    }


    private TextView
        oldQtyValue
    ,   action_ok
    ,   action_cancel;

    private EditText input;
    private BigDecimal getInputValue() {
        String tmp = input.getText().toString();

        if (tmp.length() == 0 ) {
            tmp = "0";
        }
        BigDecimal bd = BigDecimal.valueOf(Double.valueOf(tmp)).setScale(3);
        return bd;
    }

    private void setOldQtyValue(BigDecimal value) {

        DecimalFormat df = new DecimalFormat("0.000##");
        oldQtyValue.setText(String.valueOf(df.format(value)));
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.dialog_checking_list_inc_goods_qty, container, false);
        oldQtyValue = view.findViewById(R.id.oldQtyValue);
        action_ok = view.findViewById(R.id.action_ok);
        action_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iNewQtyListener != null) {
                    iNewQtyListener.onNewQty(position,getInputValue());
                }
                dismiss();
            }
        });
        action_cancel = view.findViewById(R.id.action_cancel);
        action_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iNewQtyListener != null) {
                    iNewQtyListener.onCancel();
                }
                dismiss();
            }
        });

        input = view.findViewById(R.id.input);
        if (position != null && position.measureUnitIdd == 1) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        updateUi();
        return view;
    }

    private void updateUi(){
        setOldQtyValue(position.qtyUser);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // dismiss();
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }

    public interface INewQtyListener {
        void onNewQty(CheckingListPosition position, BigDecimal newQty);
        void onCancel();
    }

}
