package ru.a5x5retail.frontproductmanagement.db.query.update;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class UpdateCheckingListIncSourceQuery extends CallableQAsync {
    String checkingListHeadGuid;

    public UpdateCheckingListIncSourceQuery( String checkingListHeadGuid) {

        this.checkingListHeadGuid = checkingListHeadGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.CheckingListIncUpdateSource (?,?)}");
    }

    @Override
    protected void SetQueryParams() {
        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++,checkingListHeadGuid);
            getStmt().registerOutParameter(parameterIndex++, Types.OTHER);
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
            returnCode = getStmt().getInt(1);
            boolean b = getStmt().getMoreResults();

        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }*/

    @Override
    protected void parseOutputVars() throws SQLException {
        eventMessage = getStmt().getString(3);
    }
}
