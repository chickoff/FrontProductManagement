package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.CheckingListManufacturerDateConverter;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListManufacturerDate;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetCheckingListIncManufacturerDateListQuery extends CallableQuery<CheckingListManufacturerDate> {

    private String checkingListHeadGuid;
    private List<CheckingListManufacturerDate> list;
    public GetCheckingListIncManufacturerDateListQuery(Connection connection, String checkingListHeadGuid) {
        super(connection);
        this.checkingListHeadGuid = checkingListHeadGuid;
        list = new ArrayList<>();
    }

    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.CheckingListIncGetManufactureDateList(?)}");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setString(2,checkingListHeadGuid);

    }

    @Override
    public void Execute() throws SQLException {
            super.Execute();
            CheckingListManufacturerDateConverter converter = new CheckingListManufacturerDateConverter();
            while (getResultSet().next()) {
                CheckingListManufacturerDate head = new CheckingListManufacturerDate();
                converter.Convert(getResultSet(),head);
                list.add(head);
            }
            stmt.getMoreResults();
            setReturnCode((int)stmt.getObject(1));
            int r = getReturnCode();
    }

    @Override
    public List<CheckingListManufacturerDate> getList() {
       return list;
    }
}
