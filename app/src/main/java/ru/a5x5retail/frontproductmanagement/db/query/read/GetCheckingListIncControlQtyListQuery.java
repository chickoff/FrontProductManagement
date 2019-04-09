package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.CheckingListControlQtyConverter;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListControlQty;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetCheckingListIncControlQtyListQuery extends CallableQuery<CheckingListControlQty> {

    private String checkingListHeadGuid;
    private List<CheckingListControlQty> list;
    public GetCheckingListIncControlQtyListQuery(Connection connection, String checkingListHeadGuid) {
        super(connection);
        this.checkingListHeadGuid = checkingListHeadGuid;
        list = new ArrayList<>();
    }

    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.CheckingListIncForControlQtyPosition(?)}");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setString(2,checkingListHeadGuid);

    }

    @Override
    public void Execute() throws SQLException {
            super.Execute();
        CheckingListControlQtyConverter converter = new CheckingListControlQtyConverter();
            while (getResultSet().next()) {
                CheckingListControlQty head = new CheckingListControlQty();
                converter.Convert(getResultSet(),head);
                list.add(head);
            }
            stmt.getMoreResults();
            setReturnCode((int)stmt.getObject(1));
            int r = getReturnCode();
    }


    @Override
    public List<CheckingListControlQty> getList() {
        return list;
    }
}
