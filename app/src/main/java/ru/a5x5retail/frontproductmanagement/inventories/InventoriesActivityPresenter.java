package ru.a5x5retail.frontproductmanagement.inventories;

import com.arellomobile.mvp.InjectViewState;

import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.filters.actualassortmentfilter.ActualAssortmentFilterFragment;
import ru.a5x5retail.frontproductmanagement.inventories.model.InventoriesModel;

@InjectViewState
public class InventoriesActivityPresenter extends BasePresenter<IInventoriesActivityView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setInventoryStatementFragment();
        InventoriesModel.createNewModel();
    }

    public void setInventorySheetsFragment() {
        getViewState().setInventorySheetsFragment();
    }

    public void setInventoryPreviewFragment() {
        getViewState().setInventoryPreviewFragment();
    }

    public void setInventoryScanGoodsFragment() {
        getViewState().setInventoryScanGoodsFragment();
    }

    public void setActualAssortmentFilterFragment(ActualAssortmentFilterFragment.IActualAssortmentFilterResultListener resultListener) {
        getViewState().setActualAssortmentFilterFragment(resultListener);
    }
}
