package ru.a5x5retail.frontproductmanagement.checkinglistinc.holders;

import android.support.annotation.NonNull;
import android.view.View;

import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListMark;

public class MarksViewHolder extends BasicViewHolder<CheckingListMark> {

    public MarksViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void setSource(CheckingListMark source) {

    }

    public static class MarksViewHolderFactory extends BasicViewHolderFactory {
        @Override
        public BasicViewHolder getNewInstance(View itemView) {
            return new MarksViewHolder(itemView);
        }
    }
}