package ru.a5x5retail.frontproductmanagement.dialogs.standart.holders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.db.models.IncomeInvoiceHead;

public class StandartViewHolder extends BasicViewHolder<IncomeInvoiceHead> {

    TextView i_def_1_textbox_1;

    public StandartViewHolder(@NonNull View itemView) {
        super(itemView);
        i_def_1_textbox_1 = itemView.findViewById(R.id.i_def_1_textbox_1);
    }

    @Override
    public void setSource(IncomeInvoiceHead source) {
        i_def_1_textbox_1.setText(source.numDoc);
    }

    public static class StandartViewHolderFactory extends BasicViewHolderFactory {

        public StandartViewHolderFactory() {
            setItemLayoutId(R.layout.item_default_1);
        }

        @Override
        public BasicViewHolder getNewInstance(View itemView) {
            return new StandartViewHolder(itemView);
        }
    }
}
