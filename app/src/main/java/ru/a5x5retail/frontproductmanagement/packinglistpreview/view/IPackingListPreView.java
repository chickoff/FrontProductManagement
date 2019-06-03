package ru.a5x5retail.frontproductmanagement.packinglistpreview.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.a5x5retail.frontproductmanagement.interfaces.IBaseMvpView;

@StateStrategyType(AddToEndStrategy.class)
public interface IPackingListPreView extends IBaseMvpView {

    void showPreviewFragment();
    void showAcceptingFragment();
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showAwaitDialog(boolean show);
    void updateUi();
}
