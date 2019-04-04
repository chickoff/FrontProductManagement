package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.InvoiceHeadConverter;
import ru.a5x5retail.frontproductmanagement.db.models.InvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetCheckingListIncIncomesQuery extends CallableQuery<InvoiceHead> {

    private String contractorGuid;
    private List<InvoiceHead> headList;

    public GetCheckingListIncIncomesQuery(Connection connection, String contractorGuid) {
        super(connection);
        headList = new ArrayList<>();
        this.contractorGuid = contractorGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("call V_StoreTSD.dbo.CheckingListIncGetListIncome ?");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
      stmt.setString(1, contractorGuid);
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
