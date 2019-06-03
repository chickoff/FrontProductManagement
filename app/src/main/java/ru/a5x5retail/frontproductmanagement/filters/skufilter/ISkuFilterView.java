package ru.a5x5retail.frontproductmanagement.filters.skufilter;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.a5x5retail.frontproductmanagement.interfaces.IBaseMvpView;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ISkuFilterView extends IBaseMvpView {
    void updateUi();
}
