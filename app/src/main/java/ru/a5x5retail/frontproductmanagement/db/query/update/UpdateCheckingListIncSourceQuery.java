package ru.a5x5retail.frontproductmanagement.db.query.update;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class UpdateCheckingListIncSourceQuery extends CallableQuery {
    String checkingListHeadGuid;

    public UpdateCheckingListIncSourceQuery(Connection connection, String checkingListHeadGuid) {
        super(connection);
        this.checkingListHeadGuid = checkingListHeadGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.CheckingListIncUpdateSource (?,?)}");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setString(2,checkingListHeadGuid);
        stmt.registerOutParameter(3, Types.OTHER);
    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        returnCode = stmt.getInt(1);
        boolean b = stmt.getMoreResults();
        eventMessage = stmt.getString(3);
    }
}
