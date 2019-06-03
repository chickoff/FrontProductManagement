package ru.a5x5retail.frontproductmanagement.db.query.create;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;
import ru.a5x5retail.frontproductmanagement.interfaces.IQuery;

public class CreateNewLocalInventoryQuery extends CallableQAsync {


    String inventoryGuid,imei,relationGuid,note;

    public CreateNewLocalInventoryQuery(String relationGuid, String imei, String note) {

        this.imei = imei;
        this.relationGuid =  relationGuid;
        this.note = note;
    }


    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.InventoryLocalHeadADD(?, ?, ?, ?)}");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++,relationGuid);
            getStmt().setString(parameterIndex++,imei);
            getStmt().setString(parameterIndex++,note);
            getStmt().registerOutParameter(parameterIndex, Types.OTHER);


    }

    /*@Override
    protected void Execute() {
        super.Execute();
        try {
            boolean b = getStmt().execute();
            returnCode = getStmt().getInt(1);

        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }*/

    @Override
    protected void parseOutputVars() throws SQLException {
        inventoryGuid = getStmt().getString(5);
    }

    public String getInventoryGuid() {
        return inventoryGuid;
    }
}
