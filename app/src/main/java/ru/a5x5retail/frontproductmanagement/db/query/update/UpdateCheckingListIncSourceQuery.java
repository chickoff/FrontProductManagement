package ru.a5x5retail.frontproductmanagement.db.query.update;

import java.sql.Connection;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class UpdateCheckingListIncSourceQuery extends CallableQuery {
    String checkingListHeadGuid;

    public UpdateCheckingListIncSourceQuery(Connection connection, String checkingListHeadGuid) {
        super(connection);
        this.checkingListHeadGuid = checkingListHeadGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("call V_StoreTSD.dbo.CheckingListIncUpdateSource (?)");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.setString(1,checkingListHeadGuid);
    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
    }
}
