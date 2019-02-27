package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.ContractorInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.converters.InvoiceHeadConverter;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorInfo;
import ru.a5x5retail.frontproductmanagement.db.models.InvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetAllContractorsQuery extends CallableQuery<ContractorInfo> {

    private String checkingListGuid;

    private List<ContractorInfo> list;

    public GetAllContractorsQuery(Connection connection) {
        super(connection);
        list = new ArrayList<>();
        this.checkingListGuid = checkingListGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("call V_StoreTSD.dbo.GetAllContractors ");
    }

    @Override
    protected void SetQueryParams() throws SQLException {

    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        ContractorInfoConverter converter = new ContractorInfoConverter();
        while (getResultSet().next()) {
            ContractorInfo head = new ContractorInfo();
            converter.Convert(getResultSet(),head);
            list.add(head);
        }
    }

    public List<ContractorInfo> getList() {
        return list;
    }
}
