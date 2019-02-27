package ru.a5x5retail.frontproductmanagement.interfaces;


public interface IRecyclerViewItemClick<T>{
    void OnClick(int pos, T innerItem);
}
