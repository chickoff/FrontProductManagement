package ru.a5x5retail.frontproductmanagement.сheckinglist.db.mssql;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.сheckinglist.db.factory.CreationObjectFactory;
import ru.a5x5retail.frontproductmanagement.сheckinglist.interfaces.IDbConvertible;

public class MsDb<T extends Object> {

    private String server,database,user,password;
    public MsDb(String server, String database, String user, String password) {
        this.server = server;
        this.database = database;
        this.user = user;
        this.password = password;

        initMsdb();
    }


    Connection connection;
    private void initMsdb(){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(getSqlConnectionString());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public List<T> ExequteQuery(String query, CreationObjectFactory<T> factory, IDbConvertible<T> converter){
        List<T> list = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                T o = factory.Create();
                converter.Convert(rs,o);
                list.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private String getSqlConnectionString(){
        return  "jdbc:jtds:sqlserver://" + server + ";databaseName=" + database + ";user=" + user + ";password=" + password;
    }

}
