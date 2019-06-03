package ru.a5x5retail.frontproductmanagement.db.query.update.async;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQueryAsync;

public class UpdateCheckingListIncSourceQueryAsync  extends CallableQAsync {
    String checkingListHeadGuid;

    public UpdateCheckingListIncSourceQueryAsync(String checkingListHeadGuid) {

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

    /*@Override
    protected void Execute() {
        super.Execute();
        try {
            boolean b = getStmt().execute();
            returnCode = getStmt().getInt(1);
            b = getStmt().getMoreResults();

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
