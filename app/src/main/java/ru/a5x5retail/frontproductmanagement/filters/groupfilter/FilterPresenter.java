package ru.a5x5retail.frontproductmanagement.filters.groupfilter;

import com.arellomobile.mvp.InjectViewState;

import ru.a5x5retail.frontproductmanagement.base.BasePresenter;


@InjectViewState
public class FilterPresenter extends BasePresenter<IFilterView> {

    public void showContractorFilter() {
        getViewState().showContractorFilter();
    }


    public void showGroupFilter() {
       getViewState().showGroupFilter();
    }
}
