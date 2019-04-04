package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.CheckingListMarkConverter;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListMark;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetCheckingListIncMarkListQuery extends CallableQuery<CheckingListMark> {

    private String checkingListHeadGuid;
    private List<CheckingListMark> list;
    public GetCheckingListIncMarkListQuery(Connection connection, String checkingListHeadGuid) {
        super(connection);
        this.checkingListHeadGuid = checkingListHeadGuid;
        list = new ArrayList<>();
    }

    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.CheckingListIncGetMarkList(?)}");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setString(2,checkingListHeadGuid);

    }

    @Override
    public void Execute() throws SQLException {
            super.Execute();
        CheckingListMarkConverter converter = new CheckingListMarkConverter();
            while (getResultSet().next()) {
                CheckingListMark head = new CheckingListMark();
                converter.Convert(getResultSet(),head);
                list.add(head);
            }
            stmt.getMoreResults();
            setReturnCode((int)stmt.getObject(1));
            int r = getReturnCode();
    }


    @Override
    public List<CheckingListMark> getList() {
        return list;
    }
}
