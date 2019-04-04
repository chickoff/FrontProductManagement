package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.CodeInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetFreeSkuForCheckigListQuery extends CallableQuery<CodeInfo> {

    private String checkingListGuid;

    private List<CodeInfo> list;

    public GetFreeSkuForCheckigListQuery(Connection connection, String checkingListGuid) {
        super(connection);
        list = new ArrayList<>();
        this.checkingListGuid = checkingListGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("call V_StoreTSD.dbo.CheckingListIncDictFreeSKU (?)");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.setString(1, checkingListGuid);
    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
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
