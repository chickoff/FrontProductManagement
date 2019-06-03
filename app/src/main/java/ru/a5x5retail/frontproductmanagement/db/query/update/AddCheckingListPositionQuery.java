package ru.a5x5retail.frontproductmanagement.db.query.update;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class AddCheckingListPositionQuery extends CallableQAsync {

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


    public AddCheckingListPositionQuery( String checkingListHeadGuid, int sku) {

        this.checkingListHeadGuid = checkingListHeadGuid;
        this.sku = sku;

    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.CheckingListIncPositionAdd (?, ?)");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++,checkingListHeadGuid);
            getStmt().setInt(parameterIndex++,sku);
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


}
