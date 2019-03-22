package ru.a5x5retail.frontproductmanagement.packinglistitems.viewmodel;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.base.BaseViewModel;
import ru.a5x5retail.frontproductmanagement.base.TypedViewModel;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;

import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetCheckingHeadListQuery;

import java.sql.SQLException;
import java.util.List;



public class PackingListItemsViewModel extends TypedViewModel {
    private List<CheckingListHead> headList;

    @Override
    public void Load() throws SQLException, ClassNotFoundException {
        super.Load();
        MsSqlConnection con = new MsSqlConnection();
        GetCheckingHeadListQuery query =
                new GetCheckingHeadListQuery(con.getConnection(), AppConfigurator.getDeviceId(ProdManApp.getAppContext()),Constants.getCurrentTypeOfDocument().getIndex());
            con.CallQuery(query);
        headList = query.getHeadList();
    }
    public List<CheckingListHead> getHeadList() {
        return headList;
    }

}
