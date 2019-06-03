package ru.a5x5retail.frontproductmanagement.db.query;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;

public class TestConnectionQuery extends BaseQuery {

    public TestConnectionQuery() throws SQLException, ClassNotFoundException {

    }

    protected void Execute()  {
        setSqlString("SELECT TOP 1 1 AS 'C'");

        try {
            connection = new MsSqlConnection().getConnection();
            Statement stmt = connection.createStatement();
            boolean successfull =  stmt.execute(getSqlString());
            setResultSetEnable(successfull);
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }
}
