package ru.a5x5retail.frontproductmanagement.db.mssql;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.interfaces.IQuery;

import static android.content.Context.MODE_PRIVATE;

public class MsSqlConnection {

    private String server,database,user,password;
    Connection connection;

    public MsSqlConnection(String server, String database, String user, String password) throws SQLException, ClassNotFoundException {
        this.server = server;
        this.database = database;
        this.user = user;
        this.password = password;
        init();
    }

    public MsSqlConnection() throws SQLException, ClassNotFoundException {
        String[] v = AppConfigurator.getConnectionString();
        this.server = v[0];
        this.database = "V_StoreTSD";
        this.user = v[1];
        this.password = v[2];
        init();
    }

    private void init() throws SQLException, ClassNotFoundException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(getSqlConnectionString());
        }

    private String getSqlConnectionString(){
        return  "jdbc:jtds:sqlserver://" + server + ";databaseName=" + database + ";user=" + user + ";password=" + password;
    }

    public Connection getConnection() {
        return connection;
    }

    public void CallQuery(IQuery query) throws SQLException {
        if (getConnection()!=null){
            query.Execute();
        }
    }
}
