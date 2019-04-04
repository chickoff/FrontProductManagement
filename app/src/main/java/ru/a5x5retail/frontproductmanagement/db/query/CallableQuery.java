package ru.a5x5retail.frontproductmanagement.db.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class CallableQuery<T> extends BaseQuery {

    protected CallableStatement stmt = null;

    public CallableQuery(Connection connection) {
        super(connection);
    }

    protected void createStatement() throws SQLException {
        if (stmt == null){
            stmt = connection.prepareCall(getSqlString());
        }
    }

    protected abstract void SetQuery();
    protected abstract void SetQueryParams() throws SQLException;

    public void Execute() throws SQLException {
        SetQuery();
        createStatement();
        SetQueryParams();
        setSuccessfull(stmt.execute());
        ResultSet rs = stmt.getResultSet();
        setResultSet(rs);
    }

    public List<T> getList(){return null;}
}
