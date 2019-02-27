package ru.a5x5retail.frontproductmanagement.printprice.db.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IQuery;


public class PrintPriceListToPrintQuery implements IQuery {
    private final String sql = "{call V_StoreTSD.[dbo].[PrintPriceListSendToPrint](?,?)}";

    CallableStatement stmt = null;
    String data;
    int TypePricePrint;
  //  String headerGUID;//,statementGuid;
   // int code,operationType;
   // int qty;


    public PrintPriceListToPrintQuery(String data, int TypePricePrint) {
       // this.imei = imei;
        this.data = data;
        this.TypePricePrint=TypePricePrint;

      //  this.code=code;
      //  this.qty=qty;
       // this.operationType=operationType;
    }


    @Override
    public void Execute(Connection connection) {
        try {
            stmt = connection.prepareCall(sql);
            stmt.setString(1,data);
            stmt.setInt(2,TypePricePrint);
           // stmt.setInt(2,code);
           // stmt.setInt(3, qty);
           // stmt.setInt(4,operationType);
            stmt.execute();
            //inventoryGuid = stmt.getString(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}