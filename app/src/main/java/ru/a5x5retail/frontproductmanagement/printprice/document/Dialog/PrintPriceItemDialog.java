package ru.a5x5retail.frontproductmanagement.printprice.document.Dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.printprice.db.models.PrintPriceItem;
import ru.a5x5retail.frontproductmanagement.printprice.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.printprice.db.query.PrintPriceItemEditQuery;
import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IOnInputSelected;


public class PrintPriceItemDialog extends DialogFragment {

    private static final String TAG = "PrintPriceItemDialog";



    public PrintPriceItemDialog(){
    }


    public IOnInputSelected mOnInputSelected;

    //widgets
    private EditText mInput;
    private TextView mActionOk, mActionCancel;
    // private BigDecimal qty;
    private PrintPriceItem innerItem;

    public void setQty(PrintPriceItem innerItem)
    {//BigDecimal _qty, UUID _fff
        //qty=_qty;
        //fff=_fff;
        this.innerItem=innerItem;

        //  mInput.setHint(String.valueOf(qty));
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_print_price_item, container, false);
        mActionOk = view.findViewById(R.id.action_okP);
        mActionCancel = view.findViewById(R.id.action_cancelP);
        mInput = view.findViewById(R.id.inputP);
        //  if (qty != null)
        // mInput.setHint(String.valueOf(qty));
        mInput.setText(String.valueOf(innerItem.Qty));


        //  mInput.
        // String dd = mInput.getText().toString();


        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: capturing input.");
               // Double.valueOf(mInput.getText().toString());

                String input = mInput.getText().toString();
                if(!input.equals("")){
                   // BigDecimal bigQty= BigDecimal.valueOf(Double.valueOf(mInput.getText().toString()));
                   // String ff=mInput.getText().toString();
                    //String ff1=ff;
                   // int fffg=Integer.parseInt(input);
                    //int bigQty=Integer.getInteger(mInput.getText().toString());


                 //  String ff= innerItem.HeaderGuid;


                    MsSqlConnection con = new MsSqlConnection();
                    PrintPriceItemEditQuery query2 = new PrintPriceItemEditQuery(innerItem.HeaderGuid
                            ,innerItem.Code
                            ,Integer.parseInt(input)
                            ,0);
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



        try{

            Object rrt = context;
            mOnInputSelected = (IOnInputSelected) rrt;
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage() );
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

