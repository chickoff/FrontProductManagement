package ru.a5x5retail.frontproductmanagement.db.query;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.interfaces.IQuery;

public class BaseQuery {
    private String sqlString;
    private boolean isResultSetEnable = false;
    private Exception exception;
    public int returnCode;
    public String eventMessage;
    Connection connection = null;
    private ResultSet rs;


    public BaseQuery() throws SQLException, ClassNotFoundException {


    }

    protected Connection getConnection() {
        Connection c = null;
        while (c ==null) {
            try {
                c = new MsSqlConnection().getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return c;
    }

    public String getSqlString() {
        return sqlString;
    }

    public void setSqlString(String sqlString) {
        this.sqlString = sqlString;
    }


    protected void Execute() throws SQLException {

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

    public Exception getException() {
        return exception;
    }

    protected void setException(Exception exception) {
        this.exception = exception;
    }
}
