package ru.a5x5retail.frontproductmanagement.newdocumentmaster.invoicemasters.decommissionspoilmaster;

import android.databinding.Bindable;

import java.sql.SQLException;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.BR;
import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.base.TypedViewModel;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.db.converters.OutgoInvoiceHeadConverter;
import ru.a5x5retail.frontproductmanagement.db.models.IncomeInvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.models.OutgoInvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.create.CreateNewDecommissionSpoilQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetDecommissionSpoilQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetExternalIncomeInvoiceQuery;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.invoicemasters.InvoiceMasterViewModel;

public class DecommissionSpoilMasterViewModel extends TypedViewModel {


    private List<OutgoInvoiceHead> invoiceHeadList;
    public void setInvoiceHeadList(List<OutgoInvoiceHead> invoiceHeadList) {
        this.invoiceHeadList = invoiceHeadList;    }

    public List<OutgoInvoiceHead> getInvoiceHeadList() {
        return invoiceHeadList;
    }

    private OutgoInvoiceHead selectedInvoiceHead;
    @Bindable
    public OutgoInvoiceHead getSelectedInvoiceHead() {
        return selectedInvoiceHead;
    }

    public void setSelectedInvoiceHead(OutgoInvoiceHead selectedInvoiceHead) {
        this.selectedInvoiceHead = selectedInvoiceHead;
        notifyPropertyChanged(BR.selectedInvoiceHead);
    }

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
