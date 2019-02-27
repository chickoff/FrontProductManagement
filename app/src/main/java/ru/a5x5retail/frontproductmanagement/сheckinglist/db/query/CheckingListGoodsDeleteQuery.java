package ru.a5x5retail.frontproductmanagement.сheckinglist.db.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import ru.a5x5retail.frontproductmanagement.сheckinglist.interfaces.IQuery;

public class CheckingListGoodsDeleteQuery  implements IQuery {
    private final String sql = "{call V_StoreTSD.[dbo].[CheckingListGoodsDelete](?)}";

    CallableStatement stmt = null;
    // String imei;
    String checkingListHeadGUID;//,statementGuid;
  //  int code,operationType;
    //  BigDecimal qty;
   // boolean checked;


    public CheckingListGoodsDeleteQuery(UUID checkingListHeadGUID) {
        // this.imei = imei;
        this.checkingListHeadGUID =  checkingListHeadGUID.toString();
       // this.code=code;
       // this.checked=checked;
        // this.operationType=operationType;
    }

    public void Execute(Connection connection) {
        try {
            stmt = connection.prepareCall(sql);
            stmt.setString(1,checkingListHeadGUID);
          //  stmt.setInt(2,code);
           // stmt.setBoolean(3, checked);
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
