package ru.a5x5retail.frontproductmanagement.db.query.update.async;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQueryAsync;

public class UpdateLocalInventoryInRrQueryAsync  extends CallableQAsync {
    String checkingListHeadGuid;

    public UpdateLocalInventoryInRrQueryAsync(String checkingListHeadGuid) {
        this.checkingListHeadGuid = checkingListHeadGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.InventoryLocalUpdateRRDocument (?, ?)");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++,checkingListHeadGuid);
            getStmt().registerOutParameter(parameterIndex++, Types.OTHER);
    }

    @Override
    protected void parseOutputVars() throws Exception {
        setEventMessage(getStmt().getString(3));
    }
}
