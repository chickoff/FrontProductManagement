package ru.a5x5retail.frontproductmanagement.newdocumentmaster.invoicemasters.externalincomemaster;

import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.create.CreateNewExternalIncomeQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetExternalIncomeInvoiceQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetInternalIncomeInvoiceQuery;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.invoicemasters.InvoiceMasterViewModel;

public class ExternalIncomeMasterViewModel extends InvoiceMasterViewModel {

    @Override
    public void Load() throws SQLException, ClassNotFoundException {
        MsSqlConnection con = new MsSqlConnection();
        GetExternalIncomeInvoiceQuery query = new GetExternalIncomeInvoiceQuery(con.getConnection());
        query.Execute();
        setInvoiceHeadList(query.getList());
    }

    public void saveSelectedDocument() throws SQLException, ClassNotFoundException {
        MsSqlConnection con = new MsSqlConnection();
        CreateNewExternalIncomeQuery query =
                new CreateNewExternalIncomeQuery(
                        con.getConnection(),
                        getSelectedInvoiceHead().guid,
                        AppConfigurator.getDeviceId(ProdManApp.getAppContext()));
        query.Execute();
    }
}
