package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.CheckingListHeadConverter;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetCheckingHeadListQuery extends CallableQuery<CheckingListHead> {

    private int typeDocId;
    private String imei;
    private List<CheckingListHead> headList;
    public GetCheckingHeadListQuery(Connection connection, String imei, Integer typeDocId) {
        super(connection);
        this.typeDocId = typeDocId;
        this.imei = imei;
        headList = new ArrayList<>();
    }

    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.CheckingListGetHead(?,?)}");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setString(2,imei);
        stmt.setInt(3,typeDocId);
    }

    @Override
    public void Execute() throws SQLException {
            super.Execute();
            CheckingListHeadConverter converter = new CheckingListHeadConverter();
            while (getResultSet().next()) {
                CheckingListHead head = new CheckingListHead();
                converter.Convert(getResultSet(),head);
                headList.add(head);
            }
            stmt.getMoreResults();
            setReturnCode((int)stmt.getObject(1));
            int r = getReturnCode();
    }

    @Override
    public List<CheckingListHead> getList() {
        return headList;
    }
}
