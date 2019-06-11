package ru.a5x5retail.frontproductmanagement.inventories;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.a5x5retail.frontproductmanagement.filters.actualassortmentfilter.ActualAssortmentFilterFragment;
import ru.a5x5retail.frontproductmanagement.interfaces.IBaseMvpView;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IInventoriesActivityView extends IBaseMvpView {
    void setInventoryStatementFragment();
    void setInventorySheetsFragment();
    void setInventoryPreviewFragment();
    void setEditInventoryStatementFragment();
    void setInventoryScanGoodsFragment();
    void setEditInventoryGoodsFragment();
    void setActualAssortmentFilterFragment(ActualAssortmentFilterFragment.IActualAssortmentFilterResultListener resultListener);
}
