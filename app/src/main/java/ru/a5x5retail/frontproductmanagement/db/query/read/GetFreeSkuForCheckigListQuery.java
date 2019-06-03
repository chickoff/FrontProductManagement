package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.CodeInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetFreeSkuForCheckigListQuery extends CallableQAsync {

    private String checkingListGuid;

    private List<CodeInfo> list;

    public GetFreeSkuForCheckigListQuery(String checkingListGuid) {

        list = new ArrayList<>();
        this.checkingListGuid = checkingListGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.CheckingListIncDictFreeSKU (?)");
    }

    @Override
    protected void SetQueryParams() {
        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++, checkingListGuid);
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }

/*
    @Override
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
    }
*/

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
