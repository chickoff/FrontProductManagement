package ru.a5x5retail.frontproductmanagement.filters.assortmentfilter;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.a5x5retail.frontproductmanagement.interfaces.IBaseMvpView;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IAssortmentFilterView extends IBaseMvpView {
    void updateView();
}
