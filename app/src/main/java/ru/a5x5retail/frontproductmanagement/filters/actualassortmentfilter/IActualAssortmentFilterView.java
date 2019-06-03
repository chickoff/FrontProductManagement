package ru.a5x5retail.frontproductmanagement.filters.actualassortmentfilter;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.a5x5retail.frontproductmanagement.interfaces.IBaseMvpView;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IActualAssortmentFilterView extends IBaseMvpView {
    void updateView();
}
