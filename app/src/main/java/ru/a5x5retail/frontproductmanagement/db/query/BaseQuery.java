package ru.a5x5retail.frontproductmanagement.db.query;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


import ru.a5x5retail.frontproductmanagement.interfaces.IQuery;

public class BaseQuery implements IQuery {
    private String sqlString;
    private boolean isResultSetEnable = false;
    private SQLException sqlException;
    public int returnCode;
    public String eventMessage;
    Connection connection = null;
    private ResultSet rs;

    public BaseQuery(Connection connection) {
        this.connection = connection;
    }

    public String getSqlString() {
        return sqlString;
    }
    public void setSqlString(String sqlString) {
        this.sqlString = sqlString;
    }
    @Override
    public void Execute() throws SQLException {

    }




    public boolean isResultSetEnable() {
        return isResultSetEnable;
    }

    protected void setResultSetEnable(boolean resultSetEnable) {
        isResultSetEnable = resultSetEnable;
    }

    public ResultSet getResultSet() {
        return rs;
    }

    public void setResultSet(ResultSet rs) {
        this.rs = rs;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public SQLException getSqlException() {
        return sqlException;
    }

    protected void setSqlException(SQLException sqlException) {
        this.sqlException = sqlException;
    }
}
