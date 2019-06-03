package ru.a5x5retail.frontproductmanagement.inventories.presenters;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.db.models.InventoryList;
import ru.a5x5retail.frontproductmanagement.db.query.DataBaseQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetLocalInventoryListQuery;
import ru.a5x5retail.frontproductmanagement.db_local.ProjectMap;
import ru.a5x5retail.frontproductmanagement.inventories.model.InventoriesModel;
import ru.a5x5retail.frontproductmanagement.inventories.view.IInventoryStatementView;


@InjectViewState
public class InventoryStatementPresenter extends BasePresenter<IInventoryStatementView> {

    private InventoriesModel model;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        model = InventoriesModel.getModel();
        loadInventoryStatement();
    }

    private void loadInventoryStatement() {
        final GetLocalInventoryListQuery q = new GetLocalInventoryListQuery(ProjectMap.getCurrentTypeDoc().getTypeOfDocument().getIndex());
        registerAwaitEvents(q);
        q.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                model.setInventoryLists(q.getInventoryList());
                getViewState().updateUi();
            }
        });

        q.ExecuteAsync();
    }

    public List<InventoryList> getInventoryList()
    {
        return model.getInventoryLists();
    }

    public void setSelectedInventoryList(InventoryList source) {
        model.setSelectedInventoryList(source);
    }
}
