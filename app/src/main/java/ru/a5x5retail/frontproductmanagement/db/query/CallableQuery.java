package ru.a5x5retail.frontproductmanagement.db.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;

public abstract class CallableQuery<T> extends BaseQuery {

    protected CallableStatement stmt = null;

    public CallableQuery() throws SQLException, ClassNotFoundException {
    }



    protected void createStatement() throws SQLException {
        if (stmt == null){
            stmt = getConnection().prepareCall(getSqlString());
        }
    }

    protected abstract void SetQuery();
    protected abstract void SetQueryParams() throws SQLException;

    protected void Execute() throws SQLException {
        SetQuery();
        createStatement();
        SetQueryParams();
        setResultSetEnable(stmt.execute());
        if (isResultSetEnable()) {
            ResultSet rs = stmt.getResultSet();
            setResultSet(rs);
       }
    }

    public List<T> getList(){return null;}
}
