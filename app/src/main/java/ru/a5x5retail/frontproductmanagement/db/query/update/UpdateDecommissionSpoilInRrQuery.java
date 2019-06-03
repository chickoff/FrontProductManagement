package ru.a5x5retail.frontproductmanagement.db.query.update;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class UpdateDecommissionSpoilInRrQuery extends CallableQAsync {
    String checkingListHeadGuid;

    public UpdateDecommissionSpoilInRrQuery(String checkingListHeadGuid) {

        this.checkingListHeadGuid = checkingListHeadGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.DecommissionSpoilUpdateRRDocument (?)");
    }

    @Override
    protected void SetQueryParams() {
        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++,checkingListHeadGuid);
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


}
