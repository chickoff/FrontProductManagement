package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.DivisionInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.models.DivisionInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetMainDivisionInfoQuery extends CallableQAsync {

    private String checkingListGuid;

    private DivisionInfo divisionInfo;

    public GetMainDivisionInfoQuery() {

        this.checkingListGuid = checkingListGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.DictionaryGetAllowedDivision ");
    }

    @Override
    protected void SetQueryParams() {
        parameterIndex = 1;
        try {
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   /* @Override
    protected void Execute() {
        super.Execute();

            boolean b = getStmt().execute();




            setResultSet(getStmt().getResultSet());



            *//*if (getResultSet().isBeforeFirst()) {
                boolean ba = getStmt().getMoreResults(0);
                ba = ba;
            }*//*


            setReturnCode();

    }*/

    @Override
    protected void parseResultSet() throws SQLException {
        DivisionInfoConverter converter = new DivisionInfoConverter();
        getResultSet().next();
        divisionInfo = new DivisionInfo();
        converter.Convert(getResultSet(), divisionInfo);
    }

    public DivisionInfo getDivisionInfo() {
        return divisionInfo;
    }
}
