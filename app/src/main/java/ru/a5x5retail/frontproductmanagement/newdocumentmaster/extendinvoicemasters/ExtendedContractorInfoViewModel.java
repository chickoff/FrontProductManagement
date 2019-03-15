package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters;

import java.sql.SQLException;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.base.TypedViewModel;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorExtendedInfo;
import ru.a5x5retail.frontproductmanagement.db.models.InvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.models.PlanIncome;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetExtendedContractorInfoQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetExternalIncomeInvoiceOnContractorQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetPlanIncomeListQuery;

public class ExtendedContractorInfoViewModel extends TypedViewModel {


    MsSqlConnection con ;

    @Override
    public void Load() throws SQLException, ClassNotFoundException {
        super.Load();
        con = new MsSqlConnection();
        GetExtendedContractorInfoQuery q = new GetExtendedContractorInfoQuery(con.getConnection(),externalContractorGuid);
        q.Execute();
        setContractorExtendedInfo(q.getContractorExtendedInfo());

        LoadPlanIncome();
        LoadIncome();
        onDataChanged();

    }

    private void LoadPlanIncome() throws SQLException {
        GetPlanIncomeListQuery q = new GetPlanIncomeListQuery(con.getConnection(),externalContractorGuid);
        q.Execute();
        planIncomeList = q.getPlanIncomeList();
    }

    private void LoadIncome() throws SQLException {
        GetExternalIncomeInvoiceOnContractorQuery q = new GetExternalIncomeInvoiceOnContractorQuery(con.getConnection(),externalContractorGuid);
        q.Execute();
        invoiceHeadList = q.getList();
    }

    private String externalContractorGuid;

    public String getExternalContractorGuid() {
        return externalContractorGuid;
    }

    public void setExternalContractorGuid(String externalContractorGuid) {
        this.externalContractorGuid = externalContractorGuid;
    }

    private ContractorExtendedInfo contractorExtendedInfo;

    public ContractorExtendedInfo getContractorExtendedInfo() {
        return contractorExtendedInfo;
    }

    public void setContractorExtendedInfo(ContractorExtendedInfo contractorExtendedInfo) {
        this.contractorExtendedInfo = contractorExtendedInfo;
    }


    private List<InvoiceHead> invoiceHeadList;
    public List<InvoiceHead> getInvoiceHeadList() {
        return invoiceHeadList;
    }

    public void setInvoiceHeadList(List<InvoiceHead> invoiceHeadList) {
        this.invoiceHeadList = invoiceHeadList;
    }


    private List<PlanIncome> planIncomeList;

    public List<PlanIncome> getPlanIncomeList() {
        return planIncomeList;
    }

    public void setPlanIncomeList(List<PlanIncome> planIncomeList) {
        this.planIncomeList = planIncomeList;
    }
}
