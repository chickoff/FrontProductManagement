package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.SubGroupInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.models.SubGroupInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetSubGroupListQuery extends CallableQuery<SubGroupInfo> {
    private List<SubGroupInfo> list;
    public GetSubGroupListQuery(Connection connection) {
        super(connection);
        list = new ArrayList<>();
    }

    @Override
    protected void SetQuery() {
        setSqlString("call V_StoreTSD.dbo.GetSubGroupList");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        SubGroupInfoConverter converter = new SubGroupInfoConverter();
        while (getResultSet().next()) {
            SubGroupInfo info = new SubGroupInfo();
            converter.Convert(getResultSet(),info);
            list.add(info);
        }
    }

    public List<SubGroupInfo> getList() {
        return list;
    }
}
