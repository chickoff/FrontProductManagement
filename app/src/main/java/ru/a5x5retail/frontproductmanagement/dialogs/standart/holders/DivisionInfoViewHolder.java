package ru.a5x5retail.frontproductmanagement.dialogs.standart.holders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.db.models.DivisionInfo;

public class DivisionInfoViewHolder extends BasicViewHolder<DivisionInfo> {

    TextView i_def_1_textbox_1,i_def_1_textbox_2,i_def_1_textbox_3,i_def_1_textbox_4;

    public DivisionInfoViewHolder(@NonNull View itemView) {
        super(itemView);

        i_def_1_textbox_1 = itemView.findViewById(R.id.i_def_1_textbox_1);
        i_def_1_textbox_2 = itemView.findViewById(R.id.i_def_1_textbox_2);
        i_def_1_textbox_3 = itemView.findViewById(R.id.i_def_1_textbox_3);
        i_def_1_textbox_4 = itemView.findViewById(R.id.i_def_1_textbox_4);
    }

    @Override
    public void setSource(DivisionInfo source) {
        i_def_1_textbox_1.setText(source.nameLong);
        i_def_1_textbox_2.setText(source.inn);
        i_def_1_textbox_3.setText(source.kpp);
        i_def_1_textbox_4.setText(source.postalAddress);
    }

    public static class DivisionInfoViewHolderFactory extends BasicViewHolderFactory {

        public DivisionInfoViewHolderFactory() {
            setItemLayoutId(R.layout.item_division_info);
        }

        @Override
        public BasicViewHolder getNewInstance(View itemView) {
            return new DivisionInfoViewHolder(itemView);
        }
    }
}
