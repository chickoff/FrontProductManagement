package ru.a5x5retail.frontproductmanagement.inventories.dialogs;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.conversions.Converter;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;


public class InventoryNoteDialogFragment extends DialogFragment {

    /*private static final String TAG = "MyCustomDialog";

    // private CheckingListGoodsRvVievHolder.ITestClick click;

    //0-замещение вводимого значения, заполнение по умолчанию сумарное количество
    //1-инкремент  вводимого значения, заполнение по умолчанию 0
    //
*/


    public void setListener(IDialogActionListener mListener) {
        this.mListener = mListener;    }

    IDialogActionListener mListener;

    public InventoryNoteDialogFragment() {

    }


    /*private CheckingListPosition position;
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
    }*/


    private TextView action_ok,action_cancel;
    private EditText input;

    /*private BigDecimal getInputValue() {
        String tmp = input.getText().toString();

        if (tmp.length() == 0 ) {
            tmp = "0";
        }
        BigDecimal bd = BigDecimal.valueOf(Double.valueOf(tmp)).setScale(3);
        return bd;
    }*/

    /*private void setOldQtyValue(BigDecimal value) {

        DecimalFormat df = new DecimalFormat("0.000##");
        oldQtyValue.setText(Converter.QtyToString(value));
    }*/

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialogfragment_inventory_note, container, false);
        action_ok = view.findViewById(R.id.action_ok);
        action_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onOk(input.getText().toString());
                }
                dismiss();
            }
        });
        action_cancel = view.findViewById(R.id.action_cancel);
        action_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        input = view.findViewById(R.id.input);


        /*if (position != null && position.measureUnitIdd == 1) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        updateUi();*/
        return view;
    }

   /* private void updateUi(){
        setOldQtyValue(position.qtyUser);

    }*/

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

    public interface IDialogActionListener {
        void onOk(String note);
        void onCancel();
    }

}
