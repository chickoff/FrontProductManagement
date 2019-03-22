package ru.a5x5retail.frontproductmanagement.db.query.create;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class CreateCheckingListIncDocQuery extends CallableQuery {

    private String  imei,
                    relationGuid,
                    headGuid;

    private int checkingListType;


    public CreateCheckingListIncDocQuery(Connection connection, String relationGuid
            , String imei,int checkingListType) {
        super(connection);

        this.imei = imei;
        this.relationGuid = relationGuid;
        this.checkingListType = checkingListType;
    }

    @Override
    protected void SetQuery() {
        setSqlString("{call V_StoreTSD.dbo.CheckingListIncDocAdd(?, ?, ?, ?)}");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.setString(1,relationGuid);
        stmt.setString(2,imei);
        stmt.setInt(3,checkingListType);
        stmt.registerOutParameter(4, Types.OTHER);
    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        headGuid = stmt.getString(4);
    }

    public String getHeadGuid() {
        return headGuid;
    }
}
