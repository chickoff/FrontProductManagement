package ru.a5x5retail.frontproductmanagement.сheckinglist.db.query;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import ru.a5x5retail.frontproductmanagement.сheckinglist.interfaces.IQuery;

public class CheckingListGoodsEditQuery implements IQuery {
    private final String sql = "{call V_StoreTSD.[dbo].[CheckingListGoodsEdit](?, ?, ?, ?)}";

    CallableStatement stmt = null;
   // String imei;
    String checkingListHeadGUID;//,statementGuid;
    int code,operationType;
    BigDecimal qty;

    /*

    @CheckingListHeadGUID uniqueidentifier
,@Code int
,@Oty money
,@OperationType int

    */

    public CheckingListGoodsEditQuery(UUID checkingListHeadGUID, int code, BigDecimal qty,int operationType) {
       // this.imei = imei;
        this.checkingListHeadGUID =  checkingListHeadGUID.toString();
        this.code=code;
        this.qty=qty;
        this.operationType=operationType;
    }

    public void Execute(Connection connection) {
        try {
            stmt = connection.prepareCall(sql);
            stmt.setString(1,checkingListHeadGUID);
            stmt.setInt(2,code);
            stmt.setBigDecimal(3, qty);
            stmt.setInt(4,operationType);
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