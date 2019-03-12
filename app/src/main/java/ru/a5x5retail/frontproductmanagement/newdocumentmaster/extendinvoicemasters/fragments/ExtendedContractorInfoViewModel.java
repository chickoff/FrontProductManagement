package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments;

import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.base.TypedViewModel;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorExtendedInfo;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetExtendedContractorInfoQuery;
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
    }

    private void LoadPlanIncome() throws SQLException {
        GetPlanIncomeListQuery q = new GetPlanIncomeListQuery(con.getConnection(),externalContractorGuid);
        q.Execute();
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
}
