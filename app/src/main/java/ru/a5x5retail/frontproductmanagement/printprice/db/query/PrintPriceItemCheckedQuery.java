package ru.a5x5retail.frontproductmanagement.printprice.db.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IQuery;


public class PrintPriceItemCheckedQuery
       implements IQuery
{
    private final String sql = "{call V_StoreTSD.[dbo].[CheckingListGoodsCheckedChange](?, ?, ?)}";

    CallableStatement stmt = null;
    // String imei;
    String checkingListHeadGUID;//,statementGuid;
    int code,operationType;
  //  BigDecimal qty;
    boolean checked;


    public PrintPriceItemCheckedQuery(String checkingListHeadGUID, int code, boolean checked) {
        // this.imei = imei;
        this.checkingListHeadGUID =  checkingListHeadGUID;
        this.code=code;
        this.checked=checked;
       // this.operationType=operationType;
    }

    public void Execute(Connection connection) {
        try {
            stmt = connection.prepareCall(sql);
            stmt.setString(1,checkingListHeadGUID);
            stmt.setInt(2,code);
            stmt.setBoolean(3, checked);
            stmt.execute();
            //inventoryGuid = stmt.getString(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 /*  public String getInventoryGuid() {
        return inventoryGuid;
    }*/
}
