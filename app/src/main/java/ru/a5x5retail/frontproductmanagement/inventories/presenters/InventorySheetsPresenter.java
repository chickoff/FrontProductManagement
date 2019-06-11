package ru.a5x5retail.frontproductmanagement.inventories.presenters;


import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.db.models.CheckListInventory;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.DataBaseQuery;
import ru.a5x5retail.frontproductmanagement.db.query.create.CreateNewLocalInventoryQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.InventoryLocalGetListInvSheetQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.checkinglisthead.GetCheckingHeadListQuery;
import ru.a5x5retail.frontproductmanagement.db_local.ProjectMap;
import ru.a5x5retail.frontproductmanagement.inventories.model.InventoriesModel;
import ru.a5x5retail.frontproductmanagement.inventories.view.IInventorySheetsView;

@InjectViewState
public class InventorySheetsPresenter extends BasePresenter<IInventorySheetsView> {


    private InventoriesModel model;
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        model = InventoriesModel.getModel();
        load();
    }

    private void load() {
        final InventoryLocalGetListInvSheetQuery q = new InventoryLocalGetListInvSheetQuery(AppConfigurator.getDeviceId(ProdManApp.getAppContext()),model.getSelectedInventoryList().inventoryGuid);
        q.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                model.setCheckListInventories(q.getList());
                getViewState().updateUi();
            }
        });


        q.addAsyncExceptionEventListener(new DataBaseQuery.IAsyncQueryExceptionListener() {
            @Override
            public void onAsyncQueryException(CallableQAsync query, Exception e) {

            }
        });

        q.addAsyncQueryEventListener(new DataBaseQuery.IAsyncQueryEventListener() {
            @Override
            public void onAsyncQueryEvent(CallableQAsync query, int returnCode, String returnMessage) {

            }
        });


        q.ExecuteAsync();
    }
    public void createCheckList(String note) {
        CreateNewLocalInventoryQuery q =
                new CreateNewLocalInventoryQuery(
                        model.getSelectedInventoryList().inventoryGuid,
                        AppConfigurator.getDeviceId(ProdManApp.getAppContext()),note);
        registerAwaitEvents(q);
        registerMsgEvents(q);
        q.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                load();
                getViewState().updateUi();
            }
        });

        q.addAsyncExceptionEventListener(new DataBaseQuery.IAsyncQueryExceptionListener() {
            @Override
            public void onAsyncQueryException(CallableQAsync query, Exception e) {

            }
        });

        q.addAsyncQueryEventListener(new DataBaseQuery.IAsyncQueryEventListener() {
            @Override
            public void onAsyncQueryEvent(CallableQAsync query, int returnCode, String returnMessage) {

            }
        });

        q.ExecuteAsync();
    }


    public List<CheckListInventory> getCheckingListHeadList() {
        return model.getCheckListInventories();
    }

    public void setSelectedInventorySheet(CheckListInventory sheet) {
        model.setSelectedInventorySheet(sheet);
    }

    public void setEditInventoryGoodsFragment() {
        getViewState().setEditInventoryGoodsFragment();
    }
}
