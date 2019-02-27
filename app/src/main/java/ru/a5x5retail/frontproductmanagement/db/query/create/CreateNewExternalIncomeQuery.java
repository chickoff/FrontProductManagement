package ru.a5x5retail.frontproductmanagement.db.query.create;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class CreateNewExternalIncomeQuery extends CallableQuery {

    String  imei,relationGuid,headGuid;


    public CreateNewExternalIncomeQuery(Connection connection, String relationGuid, String imei) {
        super(connection);
        this.imei = imei;
        this.relationGuid = relationGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("{call V_StoreTSD.dbo.IncomeFromExternalContractorHeadADD(?, ?, ?)}");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.setString(1,relationGuid);
        stmt.setString(2,imei);
        stmt.registerOutParameter(3, Types.OTHER);
    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        headGuid = stmt.getString(3);
    }

    public String getHeadGuid() {
        return headGuid;
    }
}
