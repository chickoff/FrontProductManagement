package ru.a5x5retail.frontproductmanagement.сheckinglist.db.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.сheckinglist.interfaces.IQuery;


public class CreateNewInventoryQuery implements IQuery {
    private final String sql = "{call V_StoreTSD.dbo.CheckingListHeadADD(?, ?, ?)}";
    CallableStatement stmt = null;
    String imei;
    String inventoryGuid,statementGuid;

    public CreateNewInventoryQuery(String statementGUID, String imei) {
        this.imei = imei;
        this.statementGuid =  statementGUID;
    }

    public void Execute(Connection connection) {
        try {
            stmt = connection.prepareCall(sql);
            stmt.setString(1,statementGuid);
            stmt.setString(2,imei);
            stmt.registerOutParameter(3, Types.OTHER);
            stmt.execute();
            inventoryGuid = stmt.getString(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getInventoryGuid() {
        return inventoryGuid;
    }
}
