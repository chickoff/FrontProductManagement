package ru.a5x5retail.frontproductmanagement.packinglistitems.viewmodel;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.base.TypedViewModel;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;

import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetCheckingHeadIncListQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetCheckingHeadListQuery;

import java.sql.SQLException;
import java.util.List;



public class PackingListItemsViewModel extends TypedViewModel {
    private List<CheckingListHead> headList;

    private MsSqlConnection con;

    public PackingListItemsViewModel() throws SQLException, ClassNotFoundException {
        con = new MsSqlConnection();
    }

    @Override
    public void Load() throws SQLException, ClassNotFoundException {
        super.Load();
        MsSqlConnection con = new MsSqlConnection();
        CallableQuery query = getQuery();
            con.CallQuery(query);
        headList = query.getList();
        headList.toArray();
    }

    private CallableQuery getQuery() {
        if (Constants.getCurrentDoc().getTypeOfDocument() == Constants.TypeOfDocument.INNER_INCOME ||
            Constants.getCurrentDoc().getTypeOfDocument() == Constants.TypeOfDocument.OUTER_INCOME) {
            return new GetCheckingHeadIncListQuery(con.getConnection(), AppConfigurator.getDeviceId(ProdManApp.getAppContext()),Constants.getCurrentDoc().getTypeOfDocument().getIndex());
        } else {
            return new GetCheckingHeadListQuery(con.getConnection(), AppConfigurator.getDeviceId(ProdManApp.getAppContext()),Constants.getCurrentDoc().getTypeOfDocument().getIndex());
        }
    }

    public List<CheckingListHead> getHeadList() {
        return headList;
    }

}
