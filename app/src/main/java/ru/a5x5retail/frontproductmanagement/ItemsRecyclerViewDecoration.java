package ru.a5x5retail.frontproductmanagement;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class ItemsRecyclerViewDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = 2;
    }
}