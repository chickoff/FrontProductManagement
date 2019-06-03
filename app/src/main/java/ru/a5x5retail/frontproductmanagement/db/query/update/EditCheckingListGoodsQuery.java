package ru.a5x5retail.frontproductmanagement.db.query.update;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;

public class EditCheckingListGoodsQuery extends CallableQAsync {

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
    private BigDecimal qty;
    private int code,operationType;
    private BigDecimal newQty;

    public EditCheckingListGoodsQuery(String checkingListHeadGuid, int code, BigDecimal qty, int operationType) {

        this.checkingListHeadGuid = checkingListHeadGuid;
        this.code = code;
        this.qty = qty;
        this.operationType = operationType;
        this.newQty = newQty;
    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.CheckingListGoodsEdit (?, ?, ?, ?)");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++,checkingListHeadGuid);
            getStmt().setInt(parameterIndex++,code);
            getStmt().setBigDecimal(parameterIndex++,qty);
            getStmt().setInt(parameterIndex++,operationType);

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

   /* @Override
    protected void parseOutputVars() throws SQLException {
        newQty = getStmt().getBigDecimal(6).setScale(3);
    }

    public BigDecimal getNewQty() {
        return newQty;
    }*/
}
