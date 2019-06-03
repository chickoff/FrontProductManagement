package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.CheckingListManufacturerDateConverter;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListManufacturerDate;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetCheckingListIncManufacturerDateListQuery extends CallableQAsync {

    private String checkingListHeadGuid;
    private List<CheckingListManufacturerDate> list;
    public GetCheckingListIncManufacturerDateListQuery(String checkingListHeadGuid) {

        this.checkingListHeadGuid = checkingListHeadGuid;
        list = new ArrayList<>();
    }

    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.CheckingListIncGetManufactureDateList(?)}");
    }

    @Override
    protected void SetQueryParams() {
        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++,checkingListHeadGuid);
        } catch (Exception e) {setException(e);
            e.printStackTrace();
        }


    }

 /*   @Override
    protected void Execute() {
            super.Execute();
        try {
            boolean b = getStmt().execute();
            setResultSet(getStmt().getResultSet());
            CheckingListManufacturerDateConverter converter = new CheckingListManufacturerDateConverter();
            while (getResultSet().next()) {
                CheckingListManufacturerDate head = new CheckingListManufacturerDate();
                converter.Convert(getResultSet(), head);
                list.add(head);
            }
            getStmt().getMoreResults();
            setReturnCode((int) getStmt().getObject(1));
            int r = getReturnCode();
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }
*/
    @Override
    protected void parseResultSet() throws SQLException {
        CheckingListManufacturerDateConverter converter = new CheckingListManufacturerDateConverter();
        while (getResultSet().next()) {
            CheckingListManufacturerDate head = new CheckingListManufacturerDate();
            converter.Convert(getResultSet(), head);
            list.add(head);
        }
    }

    public List<CheckingListManufacturerDate> getList() {
       return list;
    }
}
