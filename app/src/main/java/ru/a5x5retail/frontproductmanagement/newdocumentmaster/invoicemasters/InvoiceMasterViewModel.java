package ru.a5x5retail.frontproductmanagement.newdocumentmaster.invoicemasters;

import java.sql.SQLException;
import java.util.List;
import ru.a5x5retail.frontproductmanagement.base.TypedViewModel;
import ru.a5x5retail.frontproductmanagement.db.models.IncomeInvoiceHead;

public class InvoiceMasterViewModel extends TypedViewModel {

    private List<IncomeInvoiceHead> invoiceHeadList;
    private IncomeInvoiceHead selectedInvoiceHead;


    @Override
    public void Load() throws SQLException, ClassNotFoundException {
        super.Load();
    }

 /*   public List<IncomeInvoiceHead> getInvoiceHeadList() {
        return invoiceHeadList;
    }

    public void setInvoiceHeadList(List<IncomeInvoiceHead> invoiceHeadList) {
        this.invoiceHeadList = invoiceHeadList;
    }*/

    /*@Bindable
    public IncomeInvoiceHead getSelectedInvoiceHead() {
        return selectedInvoiceHead;
    }

    public void setSelectedInvoiceHead(IncomeInvoiceHead selectedInvoiceHead) {
        this.selectedInvoiceHead = selectedInvoiceHead;
        notifyPropertyChanged(BR.selectedInvoiceHead);
    }*/


}
