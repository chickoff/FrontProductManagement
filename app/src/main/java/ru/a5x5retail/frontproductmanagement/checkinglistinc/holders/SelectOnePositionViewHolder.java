package ru.a5x5retail.frontproductmanagement.checkinglistinc.holders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;

public class SelectOnePositionViewHolder extends BasicViewHolder<CheckingListPosition> {

    TextView i_def_1_textbox_1;

    public SelectOnePositionViewHolder(@NonNull View itemView) {
        super(itemView);
        i_def_1_textbox_1 = itemView.findViewById(R.id.i_def_1_textbox_1);
    }

    @Override
    public void setSource(CheckingListPosition source) {
        i_def_1_textbox_1.setText(source.nameLong);
    }

    public static class SelectOnePositionViewHolderFactory extends BasicViewHolderFactory {

        public SelectOnePositionViewHolderFactory() {
            setItemLayoutId(R.layout.item_default_1);
        }

        @Override
        public BasicViewHolder getNewInstance(View itemView) {
            return new SelectOnePositionViewHolder(itemView);
        }
    }
}
