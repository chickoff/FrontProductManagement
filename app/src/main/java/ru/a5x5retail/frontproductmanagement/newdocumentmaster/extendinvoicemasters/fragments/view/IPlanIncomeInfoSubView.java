package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments.view;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.a5x5retail.frontproductmanagement.interfaces.IBaseMvpView;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IPlanIncomeInfoSubView extends IBaseMvpView {
    void updateUi();
}
