package ru.a5x5retail.frontproductmanagement.db.query.create;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class SendExceptionFileQuery extends CallableQAsync {
    String imei;
    String exeptionFile;

    public SendExceptionFileQuery(String imei, String exeptionFile) {
        this.imei = imei;
        this.exeptionFile = exeptionFile;
    }

    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.SendExceptionFiles (?,?)}");
    }

    @Override
    protected void SetQueryParams() {
        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++,imei);
            getStmt().setString(parameterIndex++, exeptionFile);
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }

    }


    @Override
    public boolean Execute() {
        return super.Execute();
    }

    @Override
    protected void parseOutputVars() throws SQLException {
        returnCode = getStmt().getInt(1);
    }
}
