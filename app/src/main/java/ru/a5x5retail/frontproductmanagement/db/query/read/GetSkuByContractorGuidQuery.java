package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.CodeInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.converters.ContractorInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetSkuByContractorGuidQuery extends CallableQAsync {

    private String contractorGuid;

    private List<CodeInfo> list;

    public GetSkuByContractorGuidQuery(String contractorGuid) {

        list = new ArrayList<>();
        this.contractorGuid = contractorGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.GetSkuByContractorGuid (?) ");
    }

    @Override
    protected void SetQueryParams() {
        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++,contractorGuid);
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }

 /*   @Override
    protected void Execute() {
        super.Execute();
        try {
            boolean b = getStmt().execute();
            setResultSet(getStmt().getResultSet());
            CodeInfoConverter converter = new CodeInfoConverter();
            while (getResultSet().next()) {
                CodeInfo head = new CodeInfo();
                converter.Convert(getResultSet(), head);
                list.add(head);
            }
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }*/

    @Override
    protected void parseResultSet() throws SQLException {
            CodeInfoConverter converter = new CodeInfoConverter();
            while (getResultSet().next()) {
                CodeInfo head = new CodeInfo();
                converter.Convert(getResultSet(), head);
                list.add(head);
            }
    }

    public List<CodeInfo> getList() {
        return list;
    }
}
