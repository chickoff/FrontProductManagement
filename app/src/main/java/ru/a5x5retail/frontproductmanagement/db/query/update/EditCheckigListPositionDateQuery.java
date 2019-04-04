package ru.a5x5retail.frontproductmanagement.db.query.update;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class EditCheckigListPositionDateQuery extends CallableQuery {

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
    private Date date;



    public EditCheckigListPositionDateQuery(Connection connection, String checkingListHeadGuid, String positionGuid, Date date) {
        super(connection);
        this.checkingListHeadGuid = checkingListHeadGuid;
        this.positionGuid = positionGuid;
        this.date = date;

    }

    @Override
    protected void SetQuery() {
        setSqlString("call V_StoreTSD.dbo.CheckingListIncPositionEditDate (?, ?, ?)");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.setString(1,checkingListHeadGuid);
        stmt.setString(2,positionGuid);
        java.sql.Date d = new java.sql.Date(date.getTime());
        stmt.setDate(3, d);
    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        //newQty = stmt.getBigDecimal(5).setScale(3);
    }

   /* public BigDecimal getNewQty() {
        return newQty;
    }*/
}
