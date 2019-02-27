package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.InvoiceHeadConverter;
import ru.a5x5retail.frontproductmanagement.db.models.InvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetDecommissionSpoilQuery extends CallableQuery<InvoiceHead> {

    private List<InvoiceHead> list;

    public GetDecommissionSpoilQuery(Connection connection) {
        super(connection);
        list = new ArrayList<>();
    }

    @Override
    protected void SetQuery() {
        setSqlString("call V_StoreTSD.dbo.DecommissionSpoilGetList");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
       //none
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
