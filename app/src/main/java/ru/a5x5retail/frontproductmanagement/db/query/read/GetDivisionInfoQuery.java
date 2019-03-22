package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.ContractorInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.converters.DivisionInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorInfo;
import ru.a5x5retail.frontproductmanagement.db.models.DivisionInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetDivisionInfoQuery extends CallableQuery<DivisionInfo> {

    private String checkingListGuid;

    private List<DivisionInfo> list;

    public GetDivisionInfoQuery(Connection connection) {
        super(connection);
        list = new ArrayList<>();
        this.checkingListGuid = checkingListGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("call V_StoreTSD.dbo.DictionaryGetDivision ");
    }

    @Override
    protected void SetQueryParams() throws SQLException {

    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        DivisionInfoConverter converter = new DivisionInfoConverter();
        while (getResultSet().next()) {
            DivisionInfo tmp = new DivisionInfo();
            converter.Convert(getResultSet(),tmp);
            list.add(tmp);
        }
    }

    public List<DivisionInfo> getList() {
        return list;
    }
}
