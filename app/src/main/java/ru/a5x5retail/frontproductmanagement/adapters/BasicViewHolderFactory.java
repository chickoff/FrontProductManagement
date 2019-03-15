package ru.a5x5retail.frontproductmanagement.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;

public abstract class BasicViewHolderFactory<VH extends RecyclerView.ViewHolder> {
    public abstract VH getNewInstance(View itemView);
}
