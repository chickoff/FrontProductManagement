package ru.a5x5retail.frontproductmanagement.db.query.create;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class SendExceptionFileQuery extends CallableQuery {
    String imei;
    String exeptionFile;

    public SendExceptionFileQuery(Connection connection, String imei, String exeptionFile) {
        super(connection);
        this.imei = imei;
        this.exeptionFile = exeptionFile;
    }

    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.SendExceptionFiles (?,?)}");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setString(2,imei);
        stmt.setString(3, exeptionFile);
    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        returnCode = stmt.getInt(1);

        /*boolean b = stmt.getMoreResults();
        eventMessage = stmt.getString(3);*/
    }
}
