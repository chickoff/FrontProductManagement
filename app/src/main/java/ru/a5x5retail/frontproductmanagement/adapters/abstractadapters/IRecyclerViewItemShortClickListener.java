package ru.a5x5retail.frontproductmanagement.adapters.abstractadapters;

import android.view.View;

/*250*/
public interface IRecyclerViewItemShortClickListener<T>{
    //void OnShortClick(int pos, T innerItem);

    void OnShortClick(int pos, View view, T innerItem);
}
