package ru.a5x5retail.frontproductmanagement.inventories.presenters;

import com.arellomobile.mvp.InjectViewState;

import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.db.models.CheckListInventory;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.DataBaseQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.UpdateDecommissionSpoilInRrQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.async.UpdateCheckingListIncSourceQueryAsync;
import ru.a5x5retail.frontproductmanagement.db.query.update.async.UpdateLocalInventoryInRrQueryAsync;
import ru.a5x5retail.frontproductmanagement.db_local.ProjectMap;
import ru.a5x5retail.frontproductmanagement.inventories.model.InventoriesModel;
import ru.a5x5retail.frontproductmanagement.inventories.view.IInventoryPreviewView;

@InjectViewState
public class InventoryPreviewPresenter extends BasePresenter<IInventoryPreviewView> {


    private InventoriesModel model;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        model = InventoriesModel.getModel();
    }

    public void doSyncInRr() {
        CallableQAsync q = new UpdateLocalInventoryInRrQueryAsync(model.getSelectedInventorySheet().checkingListHeadGuid);
        registerAwaitEvents(q);
        q.addAsyncExceptionEventListener(new DataBaseQuery.IAsyncQueryExceptionListener() {
            @Override
            public void onAsyncQueryException(CallableQAsync query, Exception e) {
                getViewState().showExceptionToast(e,e.getMessage());
            }
        });

        final CallableQAsync finalQ = q;
        q.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                getViewState().showEventToast(finalQ.getEventMessage(),0);
            }
        });

        q.ExecuteAsync();

    }

    public void startGoods() {
        getViewState().startGoods();

    }

    public CheckListInventory getSelected() {
        return model.getSelectedInventorySheet();
    }
}
