package ru.a5x5retail.frontproductmanagement.printprice.document;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.printprice.db.models.PrintPriceItem;
import ru.a5x5retail.frontproductmanagement.printprice.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.printprice.db.query.PrintPriceItemCheckedQuery;
import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IPPChecked;
import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IViewHolderClickPosition;


public class PrintPriceItemRvVievHolder extends RecyclerView.ViewHolder  {

    private TextView tvCode;
    private TextView tvName;
    private Button tvBut;
    private CheckBox tvCbox;
    private IViewHolderClickPosition buttonClickListener;

    private IPPChecked mPPChecked;


    public PrintPriceItemRvVievHolder(@NonNull View itemView, IViewHolderClickPosition buttonClickListener/*,final Context baseContext*/)  {
        super(itemView);
        this.buttonClickListener = buttonClickListener;

        tvCode = itemView.findViewById(R.id.tvCodeP);
        tvName = itemView.findViewById(R.id.tvNameP);
        tvBut = itemView.findViewById(R.id.tvButP);
        tvCbox=itemView.findViewById(R.id.tvCboxP);
        tvCbox.setOnCheckedChangeListener(myCheckChangList);

        tvBut.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (PrintPriceItemRvVievHolder.this.buttonClickListener != null){
                            PrintPriceItemRvVievHolder.this.buttonClickListener.OnClickPosition(getAdapterPosition());
                        }
                    }
                }
        );
    }
    public void resListenerOff()
    {
        tvCbox.setOnCheckedChangeListener(null);

    }
    public void resListenerOn()
    {

        tvCbox.setOnCheckedChangeListener(myCheckChangList);
    }

    public TextView gettvCode() {
        return tvCode;
    }

    public TextView gettvName() {
        return tvName;
    }

    public Button gettvBut() {
        return tvBut;
    }

    public CheckBox gettvCbox() {
        return tvCbox;
    }

    // обработчик для чекбоксов
    CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // меняем данные товара

            PrintPriceItem ddd = (PrintPriceItem) itemView.getTag();
            MsSqlConnection con = new MsSqlConnection();
            PrintPriceItemCheckedQuery query2 = new PrintPriceItemCheckedQuery(ddd.HeaderGuid
                    ,ddd.Code
                    ,isChecked);
            con.CallQuery(query2);

            mPPChecked.OnPPItemClick(getAdapterPosition(),isChecked);
            //отослать команду в адаптер
        }
    };

    public void setmPPChecked(IPPChecked mPPChecked) {
        this.mPPChecked = mPPChecked;
    }
}
