package ru.a5x5retail.frontproductmanagement.dictionarygoods.interfaces;


public interface IRecyclerViewItemClick<T>{
    void OnClick(int pos, T innerItem);
}
