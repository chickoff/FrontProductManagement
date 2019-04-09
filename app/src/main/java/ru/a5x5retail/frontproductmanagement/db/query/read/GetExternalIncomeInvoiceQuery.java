package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.IncomeInvoiceHeadConverter;
import ru.a5x5retail.frontproductmanagement.db.models.IncomeInvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetExternalIncomeInvoiceQuery extends CallableQuery<IncomeInvoiceHead> {

    private List<IncomeInvoiceHead> headList;

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
        IncomeInvoiceHeadConverter converter = new IncomeInvoiceHeadConverter();
        while (getResultSet().next()) {
            IncomeInvoiceHead head = new IncomeInvoiceHead();
            converter.Convert(getResultSet(),head);
            headList.add(head);
        }
    }

    public List<IncomeInvoiceHead> getList() {
        return headList;
    }
}
