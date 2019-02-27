package ru.a5x5retail.frontproductmanagement.сheckinglist.db.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.сheckinglist.interfaces.IQuery;


public class CheckingListGoodsAddQuery implements IQuery {
    private final String sql = "{call V_StoreTSD.dbo.CheckingListGoodsAdd(?, ?)}";
    CallableStatement stmt = null;
    String checkingListHeadGuid, data;



    public CheckingListGoodsAddQuery(String checkingListHeadGuid, String data ) {
        this.checkingListHeadGuid = checkingListHeadGuid;
        this.data =  data;
    }

    public void Execute(Connection connection) {
        try {
            stmt = connection.prepareCall(sql);
            stmt.setString(1,checkingListHeadGuid);
            stmt.setString(2,data);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
