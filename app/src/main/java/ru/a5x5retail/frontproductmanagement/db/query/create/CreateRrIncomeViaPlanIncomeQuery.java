package ru.a5x5retail.frontproductmanagement.db.query.create;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class CreateRrIncomeViaPlanIncomeQuery extends CallableQAsync {

    /**

       @PlanIncomeGUID uniqueidentifier
       @NumDoc nvarchar(250)
       @IncomeGUID uniqueidentifier output

 	*/


    private String planIncomeGuid,numDoc,rrHeadGuid;

    public CreateRrIncomeViaPlanIncomeQuery(String planIncomeGuid,  String numDoc){

        this.planIncomeGuid =
                planIncomeGuid;
        this.numDoc =
                numDoc;
    }


    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.rrPlanIncomeCreateIncome(?, ?, ?)}");
    }

    @Override
    protected void SetQueryParams() {

        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++,planIncomeGuid);
            getStmt().setString(parameterIndex++,numDoc);
            getStmt().registerOutParameter(parameterIndex++, Types.OTHER);
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }

    }

    /*@Override
    protected void Execute(){
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
        rrHeadGuid = getStmt().getString(4);
    }

    public String getRrHeadGuid() {
        return rrHeadGuid;
    }
}
