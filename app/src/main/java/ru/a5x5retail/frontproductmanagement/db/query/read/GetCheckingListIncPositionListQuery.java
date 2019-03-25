package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.CheckingListMarkConverter;
import ru.a5x5retail.frontproductmanagement.db.converters.CheckingListPositionConverter;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListMark;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetCheckingListIncPositionListQuery extends CallableQuery<CheckingListPosition> {

    private String checkingListHeadGuid;
    private List<CheckingListPosition> headList;
    public GetCheckingListIncPositionListQuery(Connection connection, String checkingListHeadGuid) {
        super(connection);
        this.checkingListHeadGuid = checkingListHeadGuid;
        headList = new ArrayList<>();
    }

    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.CheckingListIncGetPositionList(?)}");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setString(2,checkingListHeadGuid);

    }

    @Override
    public void Execute() throws SQLException {
            super.Execute();
            CheckingListPositionConverter converter = new CheckingListPositionConverter();
            while (getResultSet().next()) {
                CheckingListPosition head = new CheckingListPosition();
                converter.Convert(getResultSet(),head);
                headList.add(head);
            }
            stmt.getMoreResults();
            setReturnCode((int)stmt.getObject(1));
            int r = getReturnCode();
    }

    public List<CheckingListPosition> getHeadList() {
        return headList;
    }
}
