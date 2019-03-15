package ru.a5x5retail.frontproductmanagement.adapters.abstractadapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;

public abstract class FirstRecyclerViewAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {


    protected BasicViewHolderFactory<VH> factory;
    public FirstRecyclerViewAdapter<T,VH> setHolderFactory(BasicViewHolderFactory factory) {
        this.factory = factory;
        return this;
    }

    protected int layout;
    public FirstRecyclerViewAdapter<T,VH> setLayout(int layout) {
        this.layout = layout;
        return this;
    }

    protected List<T> list;
    public FirstRecyclerViewAdapter<T,VH> setSourceList(List<T> source){
        list = source;
        return this;
    }


    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }



}


