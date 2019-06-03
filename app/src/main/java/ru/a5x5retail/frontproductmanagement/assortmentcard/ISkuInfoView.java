package ru.a5x5retail.frontproductmanagement.assortmentcard;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.a5x5retail.frontproductmanagement.interfaces.IBaseMvpView;
@StateStrategyType(AddToEndSingleStrategy.class)
public interface ISkuInfoView extends IBaseMvpView {
    void updateUi();
    void clearUi();
}
