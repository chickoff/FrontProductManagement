package ru.a5x5retail.frontproductmanagement.inventories.dialogs;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.conversions.Converter;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;
import ru.a5x5retail.frontproductmanagement.db.models.SKUContext;
import ru.a5x5retail.frontproductmanagement.inventories.model.InputTatem;


public class InventoryChangeQtyValueDialogFragment extends DialogFragment {

    private static final String TAG = "MyCustomDialog";

    // private CheckingListGoodsRvVievHolder.ITestClick click;

    //0-замещение вводимого значения, заполнение по умолчанию сумарное количество
    //1-инкремент  вводимого значения, заполнение по умолчанию 0
    //

    public InventoryChangeQtyValueDialogFragment() {

    }


    private InputTatem inputTatem;
    public InputTatem getInputTatem() {
        return inputTatem;
    }

    public void setInputTatem(InputTatem inputTatem) {
        this.inputTatem = inputTatem;
    }


   /* private CheckingListPosition position;
    public void setPosition(CheckingListPosition position) {
        this.position = position;
    }*/

    private INewQtyListener iNewQtyListener;
    public void setiNewQtyListener(INewQtyListener iNewQtyListener) {
        this.iNewQtyListener = iNewQtyListener;
    }

    /*private BigDecimal qtyFromBarcode;
    public void setQtyFromBarcode(BigDecimal qtyFromBarcode) {
        this.qtyFromBarcode = qtyFromBarcode;
    }*/

    private TextView
        oldQtyValue
    ,   action_ok
    ,   action_cancel
    ,   sku
    ,   heading
    ,   newQtyName;

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
        oldQtyValue.setText(Converter.QtyToString(value));
    }
    private void setNewQtyValue(BigDecimal value) {
        input.setText(Converter.QtyToString(value));
        input.selectAll();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialogfragment_inventory_change_qty_value, container, false);
        oldQtyValue = view.findViewById(R.id.oldQtyValue);
        action_ok = view.findViewById(R.id.action_ok);
        action_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iNewQtyListener != null) {
                    iNewQtyListener.onNewQty(inputTatem,getInputValue());
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


        /*****************************************************************************************/


        input = view.findViewById(R.id.input);
        input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
                    if (iNewQtyListener != null) {
                        iNewQtyListener.onNewQty(inputTatem,getInputValue());
                    }
                    dismiss();
                }
                return false;
            }
        });


        if (inputTatem.getMeasureUnitIdd() == 1) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        /*****************************************************************************************/

        sku = view.findViewById(R.id.sku);
        heading = view.findViewById(R.id.heading);

        newQtyName = view.findViewById(R.id.newQtyName);


        updateUi();
        return view;
    }

    private void updateUi(){
        setOldQtyValue(inputTatem.getCurrentQty());
        sku.setText(String.valueOf(inputTatem.getSku()));
        heading.setText(inputTatem.getNameLong());
        setNewQtyValue(inputTatem.getNewQty());

        if (inputTatem.getOperationType() == 0 ) {
            newQtyName.setText("Заменить количество:");
        }
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
        void onNewQty(InputTatem inputTatem, BigDecimal newQty);
        void onCancel();
    }

}
