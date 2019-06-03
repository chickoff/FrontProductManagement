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
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
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
    public void Load() {
        final GetDecommissionSpoilQuery query = new GetDecommissionSpoilQuery();
        query.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                setInvoiceHeadList(query.getList());
            }
        });
        query.ExecuteAsync();

    }

    public void saveSelectedDocument() {
        CreateNewDecommissionSpoilQuery query =
                new CreateNewDecommissionSpoilQuery(
                        getSelectedInvoiceHead().guid,
                        AppConfigurator.getDeviceId(ProdManApp.getAppContext()));
        query.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {

            }
        });
        query.ExecuteAsync();
    }
}
