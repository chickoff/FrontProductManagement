package ru.a5x5retail.frontproductmanagement.newdocumentmaster.invoicemasters;

import android.databinding.Bindable;
import java.sql.SQLException;
import java.util.List;
import ru.a5x5retail.frontproductmanagement.BR;
import ru.a5x5retail.frontproductmanagement.base.TypedViewModel;
import ru.a5x5retail.frontproductmanagement.db.models.InvoiceHead;

public class InvoiceMasterViewModel extends TypedViewModel {

    private List<InvoiceHead> invoiceHeadList;
    private InvoiceHead selectedInvoiceHead;


    @Override
    public void Load() throws SQLException, ClassNotFoundException {
        super.Load();
    }

    public List<InvoiceHead> getInvoiceHeadList() {
        return invoiceHeadList;
    }

    public void setInvoiceHeadList(List<InvoiceHead> invoiceHeadList) {
        this.invoiceHeadList = invoiceHeadList;
    }

    @Bindable
    public InvoiceHead getSelectedInvoiceHead() {
        return selectedInvoiceHead;
    }

    public void setSelectedInvoiceHead(InvoiceHead selectedInvoiceHead) {
        this.selectedInvoiceHead = selectedInvoiceHead;
        notifyPropertyChanged(BR.selectedInvoiceHead);
    }


}
