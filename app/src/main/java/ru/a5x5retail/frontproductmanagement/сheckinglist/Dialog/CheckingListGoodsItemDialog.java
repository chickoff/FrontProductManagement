package ru.a5x5retail.frontproductmanagement.сheckinglist.Dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.UUID;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.models.CheckingListGoods;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.query.CheckingListGoodsEditQuery;

public class CheckingListGoodsItemDialog extends DialogFragment {

    private static final String TAG = "MyCustomDialog";

    // private CheckingListGoodsRvVievHolder.ITestClick click;

    //0-замещение вводимого значения, заполнение по умолчанию сумарное количество
    //1-инкремент  вводимого значения, заполнение по умолчанию 0
    //

    public CheckingListGoodsItemDialog() {

    }

    public MyCustomDialog.OnInputSelected mOnInputSelected;

    //widgets
    private EditText mInput;
    private TextView mActionOk, mActionCancel;
    // private BigDecimal qty;
    private CheckingListGoods innerItem;
    private int mode=0;

    public void setQty(CheckingListGoods _innerItem) {//BigDecimal _qty, UUID _fff
        //qty=_qty;
        //fff=_fff;
        this.innerItem = _innerItem;

        //  mInput.setHint(String.valueOf(qty));
    }
    public void setMode(int mode) {        this.mode=mode;


    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_checking_list_goods_qty, container, false);
        mActionOk = view.findViewById(R.id.action_ok);
        mActionCancel = view.findViewById(R.id.action_cancel);

        mInput = view.findViewById(R.id.input);

        mInput.setText(String.valueOf(0));



        if(mode==1) {
            mInput.setText(String.valueOf(0));

        }

        if (innerItem.MeasureUnitIDD == 1) {
            DecimalFormat df = new DecimalFormat("0.000##");

            mInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            // mInput.setKeyListener(DigitsKeyListener.getInstance("0123456789.,"));


            // mInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            // mInput.setShowSoftInputOnFocus(true);
            if(mode==0) {
                mInput.setText(String.valueOf(df.format(innerItem.Qty)));
            }
          //  mInput.setText(String.valueOf(0));
            /////////////////////// mInput.setText(df.format(innerItem.Qty));
        } else {
            DecimalFormat df = new DecimalFormat("###");


            mInput.setInputType(InputType.TYPE_CLASS_NUMBER);


         //   mInput.setText(String.valueOf(0));
            ///////////////////// mInput.setText(df.format(innerItem.Qty));

            if(mode==0) {
                mInput.setText(String.valueOf(df.format(innerItem.Qty)));
            }

        }


        //  mInput.
        // String dd = mInput.getText().toString();


        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: capturing input.");
                Double.valueOf(mInput.getText().toString());

                String input = mInput.getText().toString();
                if (!input.equals("")) {
                    BigDecimal bigQty = BigDecimal.valueOf(Double.valueOf(mInput.getText().toString()));


                    UUID ff = innerItem.CheckingListHeadGuid;


                    MsSqlConnection con = new MsSqlConnection();
                    CheckingListGoodsEditQuery query2 = new CheckingListGoodsEditQuery(innerItem.CheckingListHeadGuid
                            , innerItem.Code
                            , bigQty
                            , mode);
                    con.CallQuery(query2);

/*
                    viewModel.Load(checkingListHeadGUID);
                    initRecyclerView();*/


                    // mOnInputSelected.sendInput(input);
                }


                getDialog().dismiss();
            }
        });

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDialog().dismiss();
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        try {

            Object rrt = context;
            mOnInputSelected = (MyCustomDialog.OnInputSelected) rrt;
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
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

}
