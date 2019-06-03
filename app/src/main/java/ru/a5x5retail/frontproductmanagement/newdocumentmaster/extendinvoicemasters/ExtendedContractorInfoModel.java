package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.models.ContractorExtendedInfo;
import ru.a5x5retail.frontproductmanagement.db.models.IncomeInvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.models.PlanIncome;

public class ExtendedContractorInfoModel {

    /**********************************************************************************************/
    private String externalContractorGuid;
    public String getExternalContractorGuid() {
        return externalContractorGuid;
    }
    public void setExternalContractorGuid(String externalContractorGuid) {
        this.externalContractorGuid = externalContractorGuid;
        contractorExtendedInfo = null;
        invoiceHeadList = null;
        planIncomeList = null;
    }

    /**********************************************************************************************/

    private ContractorExtendedInfo contractorExtendedInfo;
    public ContractorExtendedInfo getContractorExtendedInfo() {
        return contractorExtendedInfo;
    }
    public void setContractorExtendedInfo(ContractorExtendedInfo contractorExtendedInfo) {
        this.contractorExtendedInfo = contractorExtendedInfo;
    }

    /**********************************************************************************************/

    private List<IncomeInvoiceHead> invoiceHeadList;
    public List<IncomeInvoiceHead> getInvoiceHeadList() {
        return invoiceHeadList;
    }
    public void setInvoiceHeadList(List<IncomeInvoiceHead> invoiceHeadList) {
        this.invoiceHeadList = invoiceHeadList;
    }

    /**********************************************************************************************/

    private List<PlanIncome> planIncomeList;
    public List<PlanIncome> getPlanIncomeList() {
        return planIncomeList;
    }
    public void setPlanIncomeList(List<PlanIncome> planIncomeList) {
        this.planIncomeList = planIncomeList;
    }

    /**********************************************************************************************/

    private static ExtendedContractorInfoModel model;
    public static ExtendedContractorInfoModel getModel() {
        if (model == null) {
            model = new ExtendedContractorInfoModel();
        }
        return model;
    }
    public static void setModel(ExtendedContractorInfoModel model) {
        ExtendedContractorInfoModel.model = model;
    }
    public static void clearModel() {
        model = null;
    }
}
