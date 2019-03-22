package ru.a5x5retail.frontproductmanagement.db.query.create;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class CreateRrIncomeQuery extends CallableQuery {

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


    public CreateRrIncomeQuery( Connection connection, int checkListTypeId, String contractorGUID,
           String divisionGUIDin, String divisionGUIDout, String numDoc, String dateDoc) {
        super(connection);
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
        setSqlString("{call V_StoreTSD.dbo.rrCreateIncome(?, ?, ?, ?, ?, ?, ?)}");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.setInt(1,checkListTypeId);
        stmt.setString(2,contractorGUID);
        stmt.setString(3,divisionGUIDin);
        stmt.setString(4,divisionGUIDout);
        stmt.setString(5,numDoc);
        stmt.setString(6,dateDoc);
        stmt.registerOutParameter(7, Types.OTHER);
    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        rrHeadGuid = stmt.getString(7);

    }

    public String getRrHeadGuid() {
        return rrHeadGuid;
    }
}
