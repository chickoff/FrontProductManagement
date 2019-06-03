package ru.a5x5retail.frontproductmanagement.checkinglistinc.fragments;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;
import ru.a5x5retail.frontproductmanagement.interfaces.IBaseMvpView;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ICheckingListIncView extends IBaseMvpView {
    void updateUi();
    @StateStrategyType(SkipStrategy.class)
    void openEditableDialog(CheckingListPosition position);
    @StateStrategyType(SkipStrategy.class)
    void openSelectiblePositionDialog(List<CheckingListPosition> checkingListPositionList);

}
