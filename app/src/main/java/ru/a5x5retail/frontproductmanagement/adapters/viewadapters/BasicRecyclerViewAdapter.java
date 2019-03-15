package ru.a5x5retail.frontproductmanagement.adapters.viewadapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.FirstRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.IClickPositionListener;

public class BasicRecyclerViewAdapter<T> extends FirstRecyclerViewAdapter<T, BasicViewHolder>
implements IClickPositionListener
{
    private IRecyclerViewItemShortClickListener<T> shortClickListener;

    public BasicRecyclerViewAdapter<T> setShortClickListener(IRecyclerViewItemShortClickListener<T> shortClickListener) {
        this.shortClickListener = shortClickListener;
        return this;
    }

    @NonNull
    @Override
    public BasicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(layout,viewGroup,false);
        BasicViewHolder holder = factory.getNewInstance(view);
        holder.setClickPositionListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BasicViewHolder tBasicViewHolder, int i) {
        tBasicViewHolder.setSource(list.get(i));
    }

    @Override
    public void ClickPosition(int position, View view) {
        if (shortClickListener != null) {
            shortClickListener.OnShortClick(position,view,list.get(position));
        }
        return;
    }



}
