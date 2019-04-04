package ru.a5x5retail.frontproductmanagement.db.query.update;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class EditCheckigListPositionQtyQuery extends CallableQuery {

    /*
    *
    *
    *    @CheckingListIncHeadGUID uniqueidentifier
        ,@PositionGUID uniqueidentifier
        ,@Qty money
        ,@OperationType int
    *
    * */



    private String checkingListHeadGuid,positionGuid;
    private BigDecimal qty;
    private int operationType;
    private BigDecimal newQty;

    public EditCheckigListPositionQtyQuery(Connection connection, String checkingListHeadGuid,String positionGuid, BigDecimal qty,int operationType) {
        super(connection);
        this.checkingListHeadGuid = checkingListHeadGuid;
        this.positionGuid = positionGuid;
        this.qty = qty;
        this.operationType = operationType;
        this.newQty = newQty;
    }

    @Override
    protected void SetQuery() {
        setSqlString("call V_StoreTSD.dbo.CheckingListIncPositionEditQty (?, ?, ?, ?, ?)");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.setString(1,checkingListHeadGuid);
        stmt.setString(2,positionGuid);
        stmt.setBigDecimal(3,qty);
        stmt.setInt(4,operationType);
        stmt.registerOutParameter(5, Types.DECIMAL);
    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        newQty = stmt.getBigDecimal(5).setScale(3);
    }

    public BigDecimal getNewQty() {
        return newQty;
    }
}
