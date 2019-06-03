package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.IncomeInvoiceHeadConverter;
import ru.a5x5retail.frontproductmanagement.db.converters.OutgoInvoiceHeadConverter;
import ru.a5x5retail.frontproductmanagement.db.models.IncomeInvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.models.OutgoInvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetDecommissionSpoilQuery extends CallableQAsync {

    private List<OutgoInvoiceHead> list;

    public GetDecommissionSpoilQuery() {

        list = new ArrayList<>();
    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.DecommissionSpoilGetList");
    }

    @Override
    protected void SetQueryParams() {
        parameterIndex = 1;
        try {
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

/*    @Override
    protected void Execute() {
        super.Execute();
        try {
            boolean b = getStmt().execute();
            setResultSet(getStmt().getResultSet());
            OutgoInvoiceHeadConverter converter = new OutgoInvoiceHeadConverter();
            while (getResultSet().next()) {
                OutgoInvoiceHead head = new OutgoInvoiceHead();
                converter.Convert(getResultSet(), head);
                list.add(head);
            }
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }*/

    @Override
    protected void parseResultSet() throws SQLException {
            OutgoInvoiceHeadConverter converter = new OutgoInvoiceHeadConverter();
            while (getResultSet().next()) {
                OutgoInvoiceHead head = new OutgoInvoiceHead();
                converter.Convert(getResultSet(), head);
                list.add(head);
            }
    }

    public List<OutgoInvoiceHead> getList() {
        return list;
    }
}
