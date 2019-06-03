package ru.a5x5retail.frontproductmanagement.inventories.view;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.a5x5retail.frontproductmanagement.filters.actualassortmentfilter.ActualAssortmentFilterFragment;
import ru.a5x5retail.frontproductmanagement.interfaces.IBaseMvpView;
import ru.a5x5retail.frontproductmanagement.inventories.model.InputTatem;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IInventoryScanGoodsView extends IBaseMvpView {
    void updateUi();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void openEditableDialog(InputTatem source);
    @StateStrategyType(OneExecutionStateStrategy.class)
    void closeEditableDialog();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void callActualAssortmentFilter(ActualAssortmentFilterFragment.IActualAssortmentFilterResultListener resultListener);
}
