package ru.a5x5retail.frontproductmanagement.filters.groupfilter;

import java.util.List;

public interface IFilterCompleteListener<T> {
    void setResult(List<T> result);
}
