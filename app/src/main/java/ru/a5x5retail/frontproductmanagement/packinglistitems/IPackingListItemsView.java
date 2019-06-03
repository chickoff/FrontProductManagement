package ru.a5x5retail.frontproductmanagement.packinglistitems;

import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.a5x5retail.frontproductmanagement.interfaces.IBaseMvpView;

@StateStrategyType(AddToEndStrategy.class)
public interface IPackingListItemsView extends IBaseMvpView {

    void updateView();

}
