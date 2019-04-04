package ru.a5x5retail.frontproductmanagement.db.query.update;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class AddCheckigListPositionQuery extends CallableQuery {

    /*
    *
    *
    *    @CheckingListIncHeadGUID uniqueidentifier
        ,@PositionGUID uniqueidentifier
        ,@Qty money
        ,@OperationType int
    *
    * */



    private String checkingListHeadGuid;
    private int sku;


    public AddCheckigListPositionQuery(Connection connection, String checkingListHeadGuid, int sku) {
        super(connection);
        this.checkingListHeadGuid = checkingListHeadGuid;
        this.sku = sku;

    }

    @Override
    protected void SetQuery() {
        setSqlString("call V_StoreTSD.dbo.CheckingListIncPositionAdd (?, ?)");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.setString(1,checkingListHeadGuid);
        stmt.setInt(2,sku);

    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();

    }
}
