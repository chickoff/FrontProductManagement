package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.DivisionInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.models.DivisionInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetMainDivisionInfoQuery extends CallableQuery<DivisionInfo> {

    private String checkingListGuid;

    private DivisionInfo divisionInfo;

    public GetMainDivisionInfoQuery(Connection connection) {
        super(connection);
        this.checkingListGuid = checkingListGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("call V_StoreTSD.dbo.DictionaryGetAllowedDivision ");
    }

    @Override
    protected void SetQueryParams() throws SQLException {

    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        DivisionInfoConverter converter = new DivisionInfoConverter();
        getResultSet().next();
        divisionInfo = new DivisionInfo();
        converter.Convert(getResultSet(),divisionInfo);
    }

    public DivisionInfo getDivisionInfo() {
        return divisionInfo;
    }
}
