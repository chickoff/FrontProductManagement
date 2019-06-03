package ru.a5x5retail.frontproductmanagement.db.query.update;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.сheckinglist.interfaces.IQuery;


public class CheckingListGoodsAddQuery  extends CallableQAsync {

    String checkingListHeadGuid, data;
    public CheckingListGoodsAddQuery(String checkingListHeadGuid, String data ) {
        this.checkingListHeadGuid = checkingListHeadGuid;
        this.data =  data;
    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.CheckingListGoodsAdd (?, ?)");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        parameterIndex = 1;
        getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
        getStmt().setString(parameterIndex++,checkingListHeadGuid);
        getStmt().setString(parameterIndex++,data);
    }
}
