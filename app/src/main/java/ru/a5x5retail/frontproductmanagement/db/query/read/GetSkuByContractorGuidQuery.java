package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.CodeInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.converters.ContractorInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetSkuByContractorGuidQuery extends CallableQuery<CodeInfo> {

    private String contractorGuid;

    private List<CodeInfo> list;

    public GetSkuByContractorGuidQuery(Connection connection,String contractorGuid) {
        super(connection);
        list = new ArrayList<>();
        this.contractorGuid = contractorGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("call V_StoreTSD.dbo.GetSkuByContractorGuid (?) ");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.setString(1,contractorGuid);
    }

    @Override
    public void Execute() throws SQLException {
        SetQuery();
        createStatement();
        SetQueryParams();
        setResultSet(stmt.executeQuery());
        CodeInfoConverter converter = new CodeInfoConverter();
        while (getResultSet().next()) {
            CodeInfo head = new CodeInfo();
            converter.Convert(getResultSet(),head);
            list.add(head);
        }
    }

    public List<CodeInfo> getList() {
        return list;
    }
}
