package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.InvoiceHeadConverter;
import ru.a5x5retail.frontproductmanagement.db.models.InvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetExternalIncomeInvoiceQuery extends CallableQuery<InvoiceHead> {

    private List<InvoiceHead> headList;

    public GetExternalIncomeInvoiceQuery(Connection connection) {
        super(connection);
        headList = new ArrayList<>();
    }

    @Override
    protected void SetQuery() {
        setSqlString("call V_StoreTSD.dbo.IncomeFromExternalContractorGetList");
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
            headList.add(head);
        }
    }

    public List<InvoiceHead> getList() {
        return headList;
    }
}
