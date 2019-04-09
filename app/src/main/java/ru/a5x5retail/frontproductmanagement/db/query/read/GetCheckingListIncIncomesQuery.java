package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.IncomeInvoiceHeadConverter;
import ru.a5x5retail.frontproductmanagement.db.models.IncomeInvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetCheckingListIncIncomesQuery extends CallableQuery<IncomeInvoiceHead> {

    private String contractorGuid;
    private List<IncomeInvoiceHead> headList;

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
