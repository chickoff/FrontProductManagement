package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters;

import java.sql.SQLException;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.base.TypedViewModel;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorExtendedInfo;
import ru.a5x5retail.frontproductmanagement.db.models.IncomeInvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.models.PlanIncome;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQueryAsync;
import ru.a5x5retail.frontproductmanagement.db.query.create.CreateCheckingListIncDocQuery;

import ru.a5x5retail.frontproductmanagement.db.query.read.GetCheckingListIncIncomesQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetPlanIncomeListQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.async.GetExtendedContractorInfoQueryAsync;



public class ExtendedContractorInfoViewModel extends TypedViewModel {


    MsSqlConnection con ;

    @Override
    public void Load() throws SQLException, ClassNotFoundException {
        super.Load();
        con = new MsSqlConnection();
        LoadContractorInfo();
        LoadPlanIncome();
        LoadIncome();
    }

    private void LoadContractorInfo() throws SQLException {
        final GetExtendedContractorInfoQueryAsync q = new GetExtendedContractorInfoQueryAsync(con.getConnection(),externalContractorGuid);
        q.setQueryListener(new CallableQueryAsync.AsyncQueryListener() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void onPostExecute(Object o) {
                setContractorExtendedInfo(q.getContractorExtendedInfo());
                onDataChanged();
            }

            @Override
            public void onProgressUpdate(Object[] values) {

            }

            @Override
            public void onCancelled(Object o) {

            }

            @Override
            public void onCancelled() {

            }
        });
        q.ExecuteAsync();
        if (!q.isSuccessfull() && q.getSqlException() != null) {
            throw q.getSqlException();
        }
    }

    private void LoadPlanIncome() throws SQLException {
        GetPlanIncomeListQuery q = new GetPlanIncomeListQuery(con.getConnection(),externalContractorGuid);
        q.Execute();
        planIncomeList = q.getPlanIncomeList();
    }

    private void LoadIncome() throws SQLException {

        GetCheckingListIncIncomesQuery q = new GetCheckingListIncIncomesQuery(con.getConnection(),externalContractorGuid);
        q.Execute();
        invoiceHeadList = q.getList();
    }

    public void CreateNewCheckList(IncomeInvoiceHead head) throws SQLException {
        CreateCheckingListIncDocQuery query = new CreateCheckingListIncDocQuery(
                con.getConnection(),head.guid, AppConfigurator.getDeviceId(ProdManApp.getAppContext()),
                Constants.getCurrentDoc().getTypeOfDocument().getIndex(),head.sourceTypeIdd
        );
        query.Execute();
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

    private List<IncomeInvoiceHead> invoiceHeadList;
    public List<IncomeInvoiceHead> getInvoiceHeadList() {
        return invoiceHeadList;
    }

    public void setInvoiceHeadList(List<IncomeInvoiceHead> invoiceHeadList) {
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
