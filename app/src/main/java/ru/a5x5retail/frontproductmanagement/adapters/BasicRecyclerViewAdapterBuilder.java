package ru.a5x5retail.frontproductmanagement.adapters;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;

public class BasicRecyclerViewAdapterBuilder<T> {
private BasicRecyclerViewAdapter<T> adapter;
    public BasicRecyclerViewAdapterBuilder() {
        adapter = new BasicRecyclerViewAdapter<>();
    }

    public BasicRecyclerViewAdapterBuilder<T> setHolderFactory(BasicViewHolderFactory factory){
        adapter.setHolderFactory( factory);
        return this;
    }

    public BasicRecyclerViewAdapterBuilder<T> setLayout(int layout){
        adapter.setLayout( layout);
        return this;
    }

    public BasicRecyclerViewAdapterBuilder<T> setSourceList(List source){
        adapter.setSourceList(source);
        return this;
    }

    public BasicRecyclerViewAdapterBuilder<T> setShortClickListener(IRecyclerViewItemShortClickListener shortClickListener){
        adapter.setShortClickListener(shortClickListener);
        return this;
    }

   public BasicRecyclerViewAdapter<T> Build(){
        return adapter;
   }

}
