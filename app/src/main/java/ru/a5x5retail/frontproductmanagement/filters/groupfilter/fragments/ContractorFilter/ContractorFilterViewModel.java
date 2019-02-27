package ru.a5x5retail.frontproductmanagement.filters.groupfilter.fragments.ContractorFilter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.base.TypedViewModel;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorInfo;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetAllContractorsQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetSkuByContractorGuidQuery;

public class ContractorFilterViewModel extends TypedViewModel {

    private List<ContractorInfo> contractorList;

    public ContractorFilterViewModel() {
        contractorList = new ArrayList<>();
    }

    @Override
    public void Load() throws SQLException, ClassNotFoundException {
        super.Load();
        MsSqlConnection con = new MsSqlConnection();
        GetAllContractorsQuery q = new GetAllContractorsQuery(con.getConnection());
        q.Execute();
        contractorList = q.getList();
    }


    public List<CodeInfo> getCode( String contractorGuid) throws SQLException, ClassNotFoundException {
        MsSqlConnection con = new MsSqlConnection();
        GetSkuByContractorGuidQuery q = new GetSkuByContractorGuidQuery(con.getConnection(),contractorGuid);
        q.Execute();
        List<CodeInfo> l = q.getList();
        return l;

    }



    public List<ContractorInfo> getContractorList() {
        return contractorList;
    }
}
