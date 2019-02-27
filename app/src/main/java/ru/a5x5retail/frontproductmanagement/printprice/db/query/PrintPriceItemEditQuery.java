package ru.a5x5retail.frontproductmanagement.printprice.db.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IQuery;


public class PrintPriceItemEditQuery implements IQuery {
    private final String sql = "{call V_StoreTSD.[dbo].[CheckingListGoodsEdit](?, ?, ?, ?)}";

    CallableStatement stmt = null;
   // String imei;
    String headerGUID;//,statementGuid;
    int code,operationType;
    int qty;


    public PrintPriceItemEditQuery(String headerGUID, int code, int qty, int operationType) {
       // this.imei = imei;
        this.headerGUID =  headerGUID;
        this.code=code;
        this.qty=qty;
        this.operationType=operationType;
    }


    @Override
    public void Execute(Connection connection) {
        try {
            stmt = connection.prepareCall(sql);
            stmt.setString(1,headerGUID);
            stmt.setInt(2,code);
            stmt.setInt(3, qty);
            stmt.setInt(4,operationType);
            stmt.execute();
            //inventoryGuid = stmt.getString(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}