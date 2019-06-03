package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.IncomeInvoiceHeadConverter;
import ru.a5x5retail.frontproductmanagement.db.models.IncomeInvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetExternalIncomeInvoiceOnContractorQuery extends CallableQAsync {

    private String contractorGuid;
    private List<IncomeInvoiceHead> headList;

    public GetExternalIncomeInvoiceOnContractorQuery(String contractorGuid) {

        headList = new ArrayList<>();
        this.contractorGuid = contractorGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.IncomeFromExternalContractorGetListOnContractor ?");
    }

    @Override
    protected void SetQueryParams() {
        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++, contractorGuid);
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }

   /* @Override
    protected void Execute() {
        super.Execute();
        try {
            boolean b = getStmt().execute();
            setResultSet(getStmt().getResultSet());
            IncomeInvoiceHeadConverter converter = new IncomeInvoiceHeadConverter();
            while (getResultSet().next()) {
                IncomeInvoiceHead head = new IncomeInvoiceHead();
                converter.Convert(getResultSet(), head);
                headList.add(head);
            }
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }*/

    @Override
    protected void parseResultSet() throws SQLException {
        IncomeInvoiceHeadConverter converter = new IncomeInvoiceHeadConverter();
        while (getResultSet().next()) {
            IncomeInvoiceHead head = new IncomeInvoiceHead();
            converter.Convert(getResultSet(), head);
            headList.add(head);
        }
    }

    public List<IncomeInvoiceHead> getList() {
        return headList;
    }
}
