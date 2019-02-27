package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.InvoiceHeadConverter;
import ru.a5x5retail.frontproductmanagement.db.models.InvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetCheckListInfoQuery extends CallableQuery<InvoiceHead> {

    private String checkingListGuid;

    private List<InvoiceHead> list;

    public GetCheckListInfoQuery(Connection connection,String checkingListGuid) {
        super(connection);
        list = new ArrayList<>();
        this.checkingListGuid = checkingListGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("call V_StoreTSD.dbo.GetCheckingListContext (?)");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.setString(1,checkingListGuid);
    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        InvoiceHeadConverter converter = new InvoiceHeadConverter();
        while (getResultSet().next()) {
            InvoiceHead head = new InvoiceHead();
            converter.Convert(getResultSet(),head);
            list.add(head);
        }
    }

    public List<InvoiceHead> getList() {
        return list;
    }
}
