package ru.a5x5retail.frontproductmanagement.dictionarygoods.document;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.interfaces.IDGChecked;

public class DictionaryGoodsRvVievHolder extends RecyclerView.ViewHolder {

    private TextView tvCode;
    private TextView tvName;

    private CheckBox tvCbox;
  //  private IViewHolderClickPosition buttonClickListener;
  //  private IPPChecked mPPChecked;

    private IDGChecked mDGChecked;

    public DictionaryGoodsRvVievHolder(@NonNull View itemView) {
        super(itemView);

        tvCode = itemView.findViewById(R.id.tvCodeD);
        tvName = itemView.findViewById(R.id.tvNameD);

        tvCbox=itemView.findViewById(R.id.tvCboxD);
        tvCbox.setOnCheckedChangeListener(myCheckChangList);

    }

    public void EnableListener()
    {
        tvCbox.setOnCheckedChangeListener(myCheckChangList);
    }

    public void DisableListener() {
        tvCbox.setOnCheckedChangeListener(null);
    }


    public TextView gettvCode() {
        return tvCode;
    }

    public TextView gettvName() {
        return tvName;
    }


    public CheckBox gettvCbox() {
        return tvCbox;
    }


    // обработчик для чекбоксов
    CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // меняем данные товара
            mDGChecked.OnDGItemClick(getAdapterPosition(),isChecked);

          /*  DictionaryListGoods  ddd = (DictionaryListGoods) itemView.getTag();

          MsSqlConnection con = new MsSqlConnection();
            PrintPriceItemCheckedQuery query2 = new PrintPriceItemCheckedQuery(ddd.HeaderGuid
                    ,ddd.Code
                    ,isChecked);
            con.CallQuery(query2);

            mPPChecked.OnPPItemClick(getAdapterPosition(),isChecked);*/
            //отослать команду в адаптер
        }
    };

    public void setmDGChecked(IDGChecked mDGChecked) {
        this.mDGChecked = mDGChecked;
    }





}
