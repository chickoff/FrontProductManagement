package ru.a5x5retail.frontproductmanagement.db.query.create;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class CreateNewExternalIncomeQuery extends CallableQAsync {

    String  imei,relationGuid,headGuid;


    public CreateNewExternalIncomeQuery(String relationGuid, String imei) {

        this.imei = imei;
        this.relationGuid = relationGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.IncomeFromExternalContractorHeadADD(?, ?, ?)}");
    }

    @Override
    protected void SetQueryParams() {
        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++,relationGuid);
            getStmt().setString(parameterIndex++,imei);
            getStmt().registerOutParameter(parameterIndex, Types.OTHER);
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }

    }

   /* @Override
    protected void Execute() {
        super.Execute();
        try {
            boolean b = getStmt().execute();
            returnCode = getStmt().getInt(1);

        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }*/

    @Override
    protected void parseOutputVars() throws SQLException {
        headGuid = getStmt().getString(4);
    }

    public String getHeadGuid() {
        return headGuid;
    }
}
