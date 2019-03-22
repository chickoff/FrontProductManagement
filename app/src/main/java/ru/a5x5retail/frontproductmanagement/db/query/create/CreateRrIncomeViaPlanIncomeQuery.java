package ru.a5x5retail.frontproductmanagement.db.query.create;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class CreateRrIncomeViaPlanIncomeQuery extends CallableQuery {

    /**

       @PlanIncomeGUID uniqueidentifier
       @NumDoc nvarchar(250)
       @IncomeGUID uniqueidentifier output

 	*/


    private String planIncomeGuid,numDoc,rrHeadGuid;

    public CreateRrIncomeViaPlanIncomeQuery(Connection connection, String planIncomeGuid,  String numDoc) {
        super(connection);
        this.planIncomeGuid =
                planIncomeGuid;
        this.numDoc =
                numDoc;
    }


    @Override
    protected void SetQuery() {
        setSqlString("{call V_StoreTSD.dbo.rrPlanIncomeCreateIncome(?, ?, ?)}");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.setString(1,planIncomeGuid);
        stmt.setString(2,numDoc);
        stmt.registerOutParameter(3, Types.OTHER);
    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        rrHeadGuid = stmt.getString(3);

    }

    public String getRrHeadGuid() {
        return rrHeadGuid;
    }
}
