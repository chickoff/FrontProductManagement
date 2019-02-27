package ru.a5x5retail.frontproductmanagement.newdocumentmaster.invoicemasters.decommissionspoilmaster;

import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.create.CreateNewDecommissionSpoilQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetDecommissionSpoilQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetExternalIncomeInvoiceQuery;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.invoicemasters.InvoiceMasterViewModel;

public class DecommissionSpoilMasterViewModel extends InvoiceMasterViewModel {

    @Override
    public void Load() throws SQLException, ClassNotFoundException {
        MsSqlConnection con = new MsSqlConnection();
        GetDecommissionSpoilQuery query = new GetDecommissionSpoilQuery(con.getConnection());
        query.Execute();
        setInvoiceHeadList(query.getList());
    }

    public void saveSelectedDocument() throws SQLException, ClassNotFoundException {
        MsSqlConnection con = new MsSqlConnection();
        CreateNewDecommissionSpoilQuery query =
                new CreateNewDecommissionSpoilQuery(
                        con.getConnection(),
                        getSelectedInvoiceHead().guid,
                        AppConfigurator.getDeviceId(ProdManApp.getAppContext()));
        query.Execute();
    }
}
