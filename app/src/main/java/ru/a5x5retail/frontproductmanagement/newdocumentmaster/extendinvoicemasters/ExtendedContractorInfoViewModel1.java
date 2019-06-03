package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters;

import java.sql.SQLException;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.base.TypedViewModel;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db.CheckException;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorExtendedInfo;
import ru.a5x5retail.frontproductmanagement.db.models.IncomeInvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.models.PlanIncome;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQueryAsync;
import ru.a5x5retail.frontproductmanagement.db.query.create.CreateCheckingListIncDocQuery;

import ru.a5x5retail.frontproductmanagement.db.query.read.GetCheckingListIncIncomesQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetPlanIncomeListQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.async.GetExtendedContractorInfoQueryAsync;
import ru.a5x5retail.frontproductmanagement.db_local.ProjectMap;


public class ExtendedContractorInfoViewModel1 extends TypedViewModel {


    MsSqlConnection con ;

    @Override
    public void Load() throws SQLException, ClassNotFoundException {
        super.Load();
        con = new MsSqlConnection();
        LoadContractorInfo();
        LoadPlanIncome();
        LoadIncome();
    }

    private void LoadContractorInfo() {
        final GetExtendedContractorInfoQueryAsync q = new GetExtendedContractorInfoQueryAsync(externalContractorGuid);
        q.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                setContractorExtendedInfo(q.getContractorExtendedInfo());
                onDataChanged();
            }
        });

        q.ExecuteAsync();
       /* if (!q.isResultSetEnable() && q.getException() != null) {
            throw q.getException();
        }*/
    }

    private void LoadPlanIncome() {
        final GetPlanIncomeListQuery q = new GetPlanIncomeListQuery(externalContractorGuid);
        q.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                planIncomeList = q.getPlanIncomeList();
            }
        });
        q.ExecuteAsync();

    }

    private void LoadIncome() {

        final GetCheckingListIncIncomesQuery q = new GetCheckingListIncIncomesQuery(externalContractorGuid);
        q.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                invoiceHeadList = q.getList();
            }
        });
        q.ExecuteAsync();

    }

    public void CreateNewCheckList(IncomeInvoiceHead head) throws SQLException, ClassNotFoundException {
        CreateCheckingListIncDocQuery query = new CreateCheckingListIncDocQuery(
                head.guid, AppConfigurator.getDeviceId(ProdManApp.getAppContext()),
                ProjectMap.getCurrentTypeDoc().getTypeOfDocument().getIndex(),head.sourceTypeIdd
        );
        query.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {

            }
        });
        query.ExecuteAsync();
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
