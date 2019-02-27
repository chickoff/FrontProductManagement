package ru.a5x5retail.frontproductmanagement.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BasicViewHolder<T> extends RecyclerView.ViewHolder {



    private IClickPositionListener iListener;
    private View itemView;

    public BasicViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
    }
    public abstract void setSource(T source);
    public void setiListener(IClickPositionListener iListener) {
        this.iListener = iListener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasicViewHolder.this.iListener.ClickPosition(getAdapterPosition());
            }
        });
    }

    public interface IClickPositionListener{
        void ClickPosition(int position);
    }
}
