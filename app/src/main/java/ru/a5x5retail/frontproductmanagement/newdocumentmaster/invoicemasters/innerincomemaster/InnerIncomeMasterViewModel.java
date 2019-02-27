package ru.a5x5retail.frontproductmanagement.newdocumentmaster.invoicemasters.innerincomemaster;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.widget.TextView;

import java.util.Date;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.create.CreateNewInternalIncomeQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetInternalIncomeInvoiceQuery;

import ru.a5x5retail.frontproductmanagement.newdocumentmaster.invoicemasters.InvoiceMasterViewModel;

public class InnerIncomeMasterViewModel extends InvoiceMasterViewModel {

    @Override
    public void Load() throws SQLException, ClassNotFoundException {
        MsSqlConnection con = new MsSqlConnection();
        GetInternalIncomeInvoiceQuery query = new GetInternalIncomeInvoiceQuery(con.getConnection());
        query.Execute();
        setInvoiceHeadList(query.getList());
    }

    public void saveSelectedDocument() throws SQLException, ClassNotFoundException {
        MsSqlConnection con = new MsSqlConnection();
        CreateNewInternalIncomeQuery query =
                new CreateNewInternalIncomeQuery(
                        con.getConnection(),
                        getSelectedInvoiceHead().guid,
                        AppConfigurator.getDeviceId(ProdManApp.getAppContext()));
        query.Execute();
    }

}
