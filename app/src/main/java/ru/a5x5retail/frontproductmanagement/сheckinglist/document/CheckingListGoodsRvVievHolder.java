package ru.a5x5retail.frontproductmanagement.сheckinglist.document;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.models.CheckingListGoods;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.query.CheckingListGoodsCheckedQuery;
import ru.a5x5retail.frontproductmanagement.сheckinglist.interfaces.IViewHolderClickPosition;

public class CheckingListGoodsRvVievHolder extends RecyclerView.ViewHolder  {


    private TextView tvCode;
    private TextView tvName;
    private Button tvBut;
    private CheckBox tvCbox;

    private IViewHolderClickPosition buttonClickListener;


    public CheckingListGoodsRvVievHolder(@NonNull View itemView, IViewHolderClickPosition buttonClickListener/*,final Context baseContext*/)  {
        super(itemView);



        this.buttonClickListener = buttonClickListener;


        tvCode = itemView.findViewById(R.id.tvCode);
        tvName = itemView.findViewById(R.id.tvName);
        tvBut = itemView.findViewById(R.id.tvBut);
        tvCbox=itemView.findViewById(R.id.tvCbox);
        tvCbox.setOnCheckedChangeListener(myCheckChangList);

        tvBut.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (CheckingListGoodsRvVievHolder.this.buttonClickListener != null){
                            CheckingListGoodsRvVievHolder.this.buttonClickListener.OnClickPosition(getAdapterPosition());
                        }

                    }
                }
        );




    }
    public void ResListenerOff()
    {
        tvCbox.setOnCheckedChangeListener(null);

    }
    public void ResListenerOn()
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
    OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // меняем данные товара

            CheckingListGoods ddd = (CheckingListGoods) itemView.getTag();
            MsSqlConnection con = new MsSqlConnection();
            CheckingListGoodsCheckedQuery query2 = new CheckingListGoodsCheckedQuery(ddd.CheckingListHeadGuid
                    ,ddd.Code
                    ,isChecked);
            con.CallQuery(query2);
            //отослать команду в адаптер
        }
    };


}
