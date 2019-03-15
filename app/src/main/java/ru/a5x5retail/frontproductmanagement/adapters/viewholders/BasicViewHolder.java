package ru.a5x5retail.frontproductmanagement.adapters.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BasicViewHolder<T> extends RecyclerView.ViewHolder {

    private IClickPositionListener clickPositionListener;

    public BasicViewHolder(@NonNull View itemView) {
        super(itemView);
        setViewOnClickListener();
    }

    private void setViewOnClickListener() {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasicViewHolder.this.onClick(v);
            }
        });
    }

    public void onClick(View v) {
        clickPositionListener.ClickPosition(getAdapterPosition(),v);
    }

    public abstract void setSource(T source);

    public void setClickPositionListener(IClickPositionListener iListener) {
        clickPositionListener = iListener;
    }


}

