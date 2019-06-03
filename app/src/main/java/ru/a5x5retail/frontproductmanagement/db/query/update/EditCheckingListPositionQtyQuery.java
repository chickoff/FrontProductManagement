package ru.a5x5retail.frontproductmanagement.db.query.update;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class EditCheckingListPositionQtyQuery extends CallableQAsync {

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

    public EditCheckingListPositionQtyQuery( String checkingListHeadGuid, String positionGuid, BigDecimal qty, int operationType) {

        this.checkingListHeadGuid = checkingListHeadGuid;
        this.positionGuid = positionGuid;
        this.qty = qty;
        this.operationType = operationType;
        this.newQty = newQty;
    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.CheckingListIncPositionEditQty (?, ?, ?, ?, ?)");
    }

    @Override
    protected void SetQueryParams(){
        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++,checkingListHeadGuid);
            getStmt().setString(parameterIndex++,positionGuid);
            getStmt().setBigDecimal(parameterIndex++,qty);
            getStmt().setInt(parameterIndex++,operationType);
            getStmt().registerOutParameter(parameterIndex++, Types.DECIMAL);
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }

   /* @Override
    protected void Execute() {
        super.Execute();
        try {
            getStmt().execute();

        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }*/

    @Override
    protected void parseOutputVars() throws SQLException {
        newQty = getStmt().getBigDecimal(6).setScale(3);
    }

    public BigDecimal getNewQty() {
        return newQty;
    }
}
