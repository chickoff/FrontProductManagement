package ru.a5x5retail.frontproductmanagement.interfaces;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IBaseMvpView extends MvpView {

    void showAwaitDialog(boolean show);
    void showEventToast(String text, int toast_Len);
    void showExceptionToast(Exception e,String text);

}
