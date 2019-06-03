package ru.a5x5retail.frontproductmanagement.packinglistitems;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.DataBaseQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.checkinglisthead.GetCheckListHeadParentQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.checkinglisthead.GetCheckingHeadIncListQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.checkinglisthead.GetCheckingHeadListQuery;
import ru.a5x5retail.frontproductmanagement.db_local.ProjectMap;

@InjectViewState
public class PackingListItemsPresenter extends BasePresenter<IPackingListItemsView> {
    public PackingListItemsPresenter() {

    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        load();
    }

    public void load() {
        final GetCheckListHeadParentQuery query = getQuery();
        registerAwaitEvents(query);
        query.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                setHeadList(query.getList());
            }
        });

        query.ExecuteAsync();
    }

    private GetCheckListHeadParentQuery getQuery() {
        if (ProjectMap.getCurrentTypeDoc().getTypeOfDocument() == Constants.TypeOfDocument.INNER_INCOME ||
                ProjectMap.getCurrentTypeDoc().getTypeOfDocument() == Constants.TypeOfDocument.OUTER_INCOME) {
            return new GetCheckingHeadIncListQuery(AppConfigurator.getDeviceId(ProdManApp.getAppContext()),ProjectMap.getCurrentTypeDoc().getTypeOfDocument().getIndex());
        }
        else {
            return new GetCheckingHeadListQuery(AppConfigurator.getDeviceId(ProdManApp.getAppContext()),ProjectMap.getCurrentTypeDoc().getTypeOfDocument().getIndex());
        }
    }

    private List<CheckingListHead> headList;

    public List<CheckingListHead> getHeadList() {
        return headList;
    }

    protected void setHeadList(List<CheckingListHead> headList) {
        this.headList = headList;
        getViewState().updateView();
    }
}
