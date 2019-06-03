package ru.a5x5retail.frontproductmanagement.db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class PreparedQuery extends BaseQuery {
    public PreparedQuery() throws SQLException, ClassNotFoundException {

    }

    protected PreparedStatement stmt;

    protected void  createStatement() throws SQLException {
        if (stmt == null)
            stmt =  getConnection().prepareStatement(getSqlString());
    }

    protected abstract void SetQuery();
    protected abstract void SetQueryParams() throws SQLException;


    protected void prepare() throws SQLException {
        SetQuery();
        createStatement();
        SetQueryParams();
    }
    protected void Execute() throws SQLException {
        prepare();
        setResultSet(stmt.executeQuery());
    }
}
