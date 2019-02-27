package ru.a5x5retail.frontproductmanagement.dictionarygoods.db.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.сheckinglist.interfaces.IQuery;

public class DictionaryListChekedItemSentCheckingListQuery implements IQuery {
    private final String sql = "{call V_StoreTSD.[dbo].[DictionaryCheckedSendCheckingList](?, ?)}";

    CallableStatement stmt = null;
    String checkingListHeadGUID;
    int code;




    public DictionaryListChekedItemSentCheckingListQuery(String checkingListHeadGUID, int code) {

        this.checkingListHeadGUID =  checkingListHeadGUID;
        this.code=code;

    }

    public void Execute(Connection connection) {
        try {
            stmt = connection.prepareCall(sql);
            stmt.setString(1,checkingListHeadGUID);
            stmt.setInt(2,code);
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