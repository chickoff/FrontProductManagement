package ru.a5x5retail.frontproductmanagement.db.query;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConnectionQuery extends BaseQuery {

    public TestConnectionQuery(Connection connection) {
        super(connection);
    }

    public void Execute() {
        setSqlString("SELECT TOP 1 1 AS 'C'");
        try {
            Statement stmt = connection.createStatement();
            boolean successfull =  stmt.execute(getSqlString());
            setResultSetEnable(successfull);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
