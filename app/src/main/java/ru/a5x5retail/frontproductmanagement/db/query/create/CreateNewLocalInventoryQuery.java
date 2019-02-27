package ru.a5x5retail.frontproductmanagement.db.query.create;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;
import ru.a5x5retail.frontproductmanagement.interfaces.IQuery;

public class CreateNewLocalInventoryQuery extends CallableQuery {


    String inventoryGuid,imei,relationGuid;

    public CreateNewLocalInventoryQuery(Connection connection, String relationGuid, String imei) {
        super(connection);
        this.imei = imei;
        this.relationGuid =  relationGuid;
    }


    @Override
    protected void SetQuery() {
        setSqlString("{call V_StoreTSD.dbo.InventoryLocalHeadADD(?, ?, ?)}");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.setString(1,relationGuid);
        stmt.setString(2,imei);
        stmt.registerOutParameter(3, Types.OTHER);
    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        inventoryGuid = stmt.getString(3);

    }

    public String getInventoryGuid() {
        return inventoryGuid;
    }
}
