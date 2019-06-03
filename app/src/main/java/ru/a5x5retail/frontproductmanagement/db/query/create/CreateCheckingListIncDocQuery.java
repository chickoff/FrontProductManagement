package ru.a5x5retail.frontproductmanagement.db.query.create;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class CreateCheckingListIncDocQuery extends CallableQAsync {

    private String  imei,
                    relationGuid,
                    headGuid;

    private int checkingListType,sourceTypeIdd;


    public CreateCheckingListIncDocQuery( String relationGuid
            , String imei,int checkingListType, int sourceTypeIdd) {


        this.imei = imei;
        this.relationGuid = relationGuid;
        this.checkingListType = checkingListType;
        this.sourceTypeIdd = sourceTypeIdd;
    }

    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.CheckingListIncDocAdd(?, ?, ?, ?, ?)}");
    }

    @Override
    protected void SetQueryParams() {
        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++,relationGuid);
            getStmt().setString(parameterIndex++,imei);
            getStmt().setInt(parameterIndex++,checkingListType);
            getStmt().setInt(parameterIndex++,sourceTypeIdd);
            getStmt().registerOutParameter(parameterIndex, Types.OTHER);
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

        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }*/

    @Override
    protected void parseResultSet() throws Exception {

    }

    @Override
    protected void parseOutputVars() throws SQLException {
        headGuid = getStmt().getString(6);
    }

    public String getHeadGuid() {
        return headGuid;
    }
}
