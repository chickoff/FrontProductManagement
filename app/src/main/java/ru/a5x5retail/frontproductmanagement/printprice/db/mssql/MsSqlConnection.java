package ru.a5x5retail.frontproductmanagement.printprice.db.mssql;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IQuery;


public class MsSqlConnection {

    private String server,database,user,password;
    Connection connection;

    public MsSqlConnection(String server, String database, String user, String password) {
        this.server = server;
        this.database = database;
        this.user = user;
        this.password = password;
        init();
    }

    public MsSqlConnection(){
        String[] v = AppConfigurator.getConnectionString();
        this.server = v[0];
        this.database = "V_StoreTSD";
        this.user = v[1];
        this.password = v[2];
        init();
    }

    private void init(){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(getSqlConnectionString());
        } catch (ClassNotFoundException | SQLException e) {
            ProdManApp.Alerts.MakeToast(null,"Отсустствует подключение к серверу!",0);
            e.printStackTrace();
        }
    }

    private String getSqlConnectionString(){
        return  "jdbc:jtds:sqlserver://" + server + ";databaseName=" + database + ";user=" + user + ";password=" + password;
    }

    public Connection getConnection() {
        return connection;
    }

    public void CallQuery(IQuery query){
        if (getConnection()!=null){
            query.Execute(getConnection());
        }
    }
}
