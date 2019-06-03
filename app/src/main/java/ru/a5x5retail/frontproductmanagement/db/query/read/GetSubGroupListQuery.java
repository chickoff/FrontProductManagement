package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.SubGroupInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.models.SubGroupInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetSubGroupListQuery extends CallableQAsync {
    private List<SubGroupInfo> list;
    public GetSubGroupListQuery() {

        list = new ArrayList<>();
    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.GetSubGroupList");
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

    @Override
    protected void parseResultSet() throws SQLException {
            SubGroupInfoConverter converter = new SubGroupInfoConverter();
            while (getResultSet().next()) {
                SubGroupInfo info = new SubGroupInfo();
                converter.Convert(getResultSet(), info);
                list.add(info);
            }
    }

    public List<SubGroupInfo> getList() {
        return list;
    }
}
