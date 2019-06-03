package ru.a5x5retail.frontproductmanagement.db.query.create;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class CreateRrIncomeQuery extends CallableQAsync {

    /**

     @CheckListTypeId int
	,@ContractorGUID uniqueidentifier
	,@DivisionGUIDin uniqueidentifier
	,@DivisionGUIDout uniqueidentifier
	,@NumDoc nvarchar(250)
	,@DateDoc date
	--,@DivisionPlacementGUIDin uniqueidentifier
	--,@DivisionPlacementGUIDout uniqueidentifier
 	,@RRHeadGUID uniqueidentifier output

 	*/

    private int checkListTypeId;
    private String contractorGUID,divisionGUIDin,divisionGUIDout,numDoc,dateDoc,rrHeadGuid;


    public CreateRrIncomeQuery( int checkListTypeId, String contractorGUID,
           String divisionGUIDin, String divisionGUIDout, String numDoc, String dateDoc) {

        this.checkListTypeId =
                checkListTypeId;
        this.contractorGUID =
                contractorGUID;
        this.divisionGUIDin =
                divisionGUIDin;
        this.divisionGUIDout =
                divisionGUIDout;
        this.numDoc =
                numDoc;
        this.dateDoc =
                dateDoc;

    }


    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.rrCreateIncome(?, ?, ?, ?, ?, ?, ?)}");
    }

    @Override
    protected void SetQueryParams()  {
        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setInt(parameterIndex++,checkListTypeId);
            getStmt().setString(parameterIndex++,contractorGUID);
            getStmt().setString(parameterIndex++,divisionGUIDin);
            getStmt().setString(parameterIndex++,divisionGUIDout);
            getStmt().setString(parameterIndex++,numDoc);
            getStmt().setString(parameterIndex++,dateDoc);
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
    protected void parseOutputVars() throws SQLException {
        rrHeadGuid = getStmt().getString(8);
    }

    public String getRrHeadGuid() {
        return rrHeadGuid;
    }
}
