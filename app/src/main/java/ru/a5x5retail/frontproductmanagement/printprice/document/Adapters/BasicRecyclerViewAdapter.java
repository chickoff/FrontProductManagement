package ru.a5x5retail.frontproductmanagement.printprice.document.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class BasicRecyclerViewAdapter<T> extends RecyclerView.Adapter<BasicViewHolder>
implements BasicViewHolder.IClickPositionListener
{

    private List<T> list;
    private int layout;
    private BasicViewHolderFactory factory;
    private IRecyclerViewItemShortClickListener<T> shortClickListener;

    public BasicRecyclerViewAdapter<T> setSourceList(List source){
         list = source;
         return this;
    }

    public BasicRecyclerViewAdapter<T> setLayout(int layout) {
        this.layout = layout;
        return this;
    }

    public BasicRecyclerViewAdapter<T> setShortClickListener(IRecyclerViewItemShortClickListener<T> shortClickListener) {
        this.shortClickListener = shortClickListener;
        return this;
    }

    public BasicRecyclerViewAdapter<T> setHolderFactory(BasicViewHolderFactory factory) {
        this.factory = factory;
        return this;
    }

    @NonNull
    @Override
    public BasicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(layout,viewGroup,false);
        BasicViewHolder holder = factory.getNewInstance(view);
        holder.setiListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BasicViewHolder tBasicViewHolder, int i) {
        tBasicViewHolder.setSource(list.get(i));
    }

    @Override
    public int getItemCount() {
       if (list == null) return 0;
       return list.size();
    }


    @Override
    public void ClickPosition(int position) {
        shortClickListener.OnShortClick(position, list.get(position));
        return;
    }

    public interface IRecyclerViewItemShortClickListener<T>{
        void OnShortClick(int pos, T innerItem);
    }

}
