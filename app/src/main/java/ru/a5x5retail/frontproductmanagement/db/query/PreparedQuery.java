package ru.a5x5retail.frontproductmanagement.db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class PreparedQuery extends BaseQuery {
    public PreparedQuery(Connection connection) {
        super(connection);
    }

    protected PreparedStatement stmt;

    protected void  createStatement() throws SQLException {
        if (stmt == null)
            stmt = connection.prepareStatement(getSqlString());
    }

    protected abstract void SetQuery();
    protected abstract void SetQueryParams() throws SQLException;


    protected void prepare() throws SQLException {
        SetQuery();
        createStatement();
        SetQueryParams();
    }
    public void Execute() throws SQLException {
        prepare();
        setResultSet(stmt.executeQuery());
    }
}
