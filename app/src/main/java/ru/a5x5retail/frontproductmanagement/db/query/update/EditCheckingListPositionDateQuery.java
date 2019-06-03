package ru.a5x5retail.frontproductmanagement.db.query.update;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class EditCheckingListPositionDateQuery extends CallableQAsync {

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



    public EditCheckingListPositionDateQuery(String checkingListHeadGuid, String positionGuid, Date date) {
        this.checkingListHeadGuid = checkingListHeadGuid;
        this.positionGuid = positionGuid;
        this.date = date;

    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.CheckingListIncPositionEditDate (?, ?, ?)");
    }

    @Override
    protected void SetQueryParams() {
        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++,checkingListHeadGuid);
            getStmt().setString(parameterIndex++,positionGuid);
            java.sql.Date d = new java.sql.Date(date.getTime());
            getStmt().setDate(parameterIndex++, d);
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }

  /*  @Override
    protected void Execute() {
        super.Execute();
        try {
            getStmt().execute();
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }        //newQty = getStmt().getBigDecimal(5).setScale(3);
    }*/

   /* public BigDecimal getNewQty() {
        return newQty;
    }*/
}
