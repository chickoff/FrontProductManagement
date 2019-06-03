package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.ContractorInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.converters.DivisionInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorInfo;
import ru.a5x5retail.frontproductmanagement.db.models.DivisionInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetDivisionInfoQuery extends CallableQAsync {

    private String checkingListGuid;

    private List<DivisionInfo> list;

    public GetDivisionInfoQuery() {

        list = new ArrayList<>();
        this.checkingListGuid = checkingListGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.DictionaryGetDivision ");
    }

    @Override
    protected void SetQueryParams() {
        parameterIndex = 1;
        try {
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
        } catch (SQLException e) {
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
            DivisionInfoConverter converter = new DivisionInfoConverter();
            while (getResultSet().next()) {
                DivisionInfo tmp = new DivisionInfo();
                converter.Convert(getResultSet(), tmp);
                list.add(tmp);
            }
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }
*/

    @Override
    protected void parseResultSet() throws SQLException {
            DivisionInfoConverter converter = new DivisionInfoConverter();
            while (getResultSet().next()) {
                DivisionInfo tmp = new DivisionInfo();
                converter.Convert(getResultSet(), tmp);
                list.add(tmp);
            }
    }

    public List<DivisionInfo> getList() {
        return list;
    }
}
