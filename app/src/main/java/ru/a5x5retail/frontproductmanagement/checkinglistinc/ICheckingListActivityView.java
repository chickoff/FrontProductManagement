package ru.a5x5retail.frontproductmanagement.checkinglistinc;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.a5x5retail.frontproductmanagement.interfaces.IBaseMvpView;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ICheckingListActivityView extends IBaseMvpView {
    void showPositions();
    void showManufactor();
    void showMarks();
}
