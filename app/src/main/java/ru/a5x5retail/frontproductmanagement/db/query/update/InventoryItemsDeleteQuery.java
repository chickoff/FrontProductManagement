package ru.a5x5retail.frontproductmanagement.db.query.update;

import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;


public class InventoryItemsDeleteQuery extends CallableQAsync {

    String checkingListHeadGuid, data;
    public InventoryItemsDeleteQuery(String checkingListHeadGuid, String data ) {
        this.checkingListHeadGuid = checkingListHeadGuid;
        this.data =  data;
    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.InventoryLocalCheckingListItemDelete (?, ?)");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        parameterIndex = 1;
        getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
        getStmt().setString(parameterIndex++,checkingListHeadGuid);
        getStmt().setString(parameterIndex++,data);
    }
}
