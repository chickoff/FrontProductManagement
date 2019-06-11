package ru.a5x5retail.frontproductmanagement.db.query.update;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;

public class EditInventoryGoodsQuery extends CallableQAsync {


    int typeDocId;
    String inventoryGuid, imei, dataXml;


    public EditInventoryGoodsQuery(String inventoryGuid, int typeDocId, String imei, String dataXml) {

        this.inventoryGuid = inventoryGuid;
        this.typeDocId = typeDocId;
        this.imei = imei;
        this.dataXml = dataXml;

    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.InventoryLocalGetSharedInvSheetUpdate (?, ?, ?, ?, ?)");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++,inventoryGuid);
            getStmt().setInt(parameterIndex++,typeDocId);
            getStmt().setString(parameterIndex++,imei);
            getStmt().setString(parameterIndex++,dataXml);
            getStmt().registerOutParameter(parameterIndex++, Types.OTHER);

    }

    @Override
    protected void parseOutputVars() throws Exception {
        eventMessage = getStmt().getString(6);
    }
}
