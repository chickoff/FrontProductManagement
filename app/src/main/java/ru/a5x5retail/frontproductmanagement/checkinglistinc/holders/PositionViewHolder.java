package ru.a5x5retail.frontproductmanagement.checkinglistinc.holders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.fragments.Te;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;

public class PositionViewHolder extends BasicViewHolder<CheckingListPosition> {


    private TextView text_view_1, text_view_2, text_view_3, text_view_4, text_view_5, text_view_6, text_view_7;

    private CheckingListPosition source;
    private Te buttonClickListener;

    public PositionViewHolder(@NonNull View itemView) {
        super(itemView);

        text_view_1 = itemView.findViewById(R.id.text_view_1);
        text_view_2 = itemView.findViewById(R.id.text_view_2);
        text_view_3 = itemView.findViewById(R.id.text_view_3);
        text_view_4 = itemView.findViewById(R.id.text_view_4);
        text_view_5 = itemView.findViewById(R.id.text_view_5);
        text_view_6 = itemView.findViewById(R.id.text_view_6);
        text_view_7 = itemView.findViewById(R.id.text_view_7);

    }

    @Override
    public void setSource(final CheckingListPosition source) {
        this.source = source;
        text_view_1.setText(String.valueOf(this.source.orderBy));
        text_view_2.setText(String.valueOf(this.source.code));
        text_view_3.setText(String.valueOf(this.source.nameLong));
        text_view_4.setText(String.valueOf(this.source.price));
        text_view_5.setText(String.valueOf(this.source.vat) + "%");
        text_view_6.setText(String.valueOf(this.source.incomeGoodsQty));
        text_view_7.setText(String.valueOf(this.source.qtyUser));
        text_view_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonClickListener != null) {
                    buttonClickListener.onButtonClick(source);
                }
            }
        });


        int colorId = 0;
        switch (source.compareQty()) {
            case -1:
                colorId = R.color.max_font_color;
                break;
            case 0:
                colorId = R.color.normal_font_color;
                break;
            case 1:
                colorId = R.color.min_font_color;
                break;
        }
        text_view_7.setTextColor(ProdManApp.getAppContext().getResources().getColor(colorId));





        if(source.measureUnitIdd == 1)
        {
            DecimalFormat df = new DecimalFormat("0.000##");

            //button_1.setText(df.format(this.source.qtyUser));
        }
        else
        {
            Integer valInt= source.qtyUser.toBigInteger().intValue();
            //button_1.setText( valInt.toString());

            /*    button_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (buttonClickListener != null) {
                            buttonClickListener.onButtonClick(source);
                        }
                    }
                });*/
        }
    }

    public void setQtyButtonClickListener(Te listener) {
        this.buttonClickListener = listener;
    }


    public static class PositionViewHolderFactory extends BasicViewHolderFactory {
        public PositionViewHolderFactory() {
                setItemLayoutId(R.layout.item_checking_list_positions);
        }

        @Override
        public BasicViewHolder getNewInstance(View itemView) {
            return new PositionViewHolder(itemView);
        }
    }
}
