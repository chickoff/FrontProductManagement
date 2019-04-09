package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.IncomeInvoiceHeadConverter;
import ru.a5x5retail.frontproductmanagement.db.converters.OutgoInvoiceHeadConverter;
import ru.a5x5retail.frontproductmanagement.db.models.IncomeInvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.models.OutgoInvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetDecommissionSpoilQuery extends CallableQuery<OutgoInvoiceHead> {

    private List<OutgoInvoiceHead> list;

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
        OutgoInvoiceHeadConverter converter = new OutgoInvoiceHeadConverter();
        while (getResultSet().next()) {
            OutgoInvoiceHead head = new OutgoInvoiceHead();
            converter.Convert(getResultSet(),head);
            list.add(head);
        }
    }

    public List<OutgoInvoiceHead> getList() {
        return list;
    }
}
