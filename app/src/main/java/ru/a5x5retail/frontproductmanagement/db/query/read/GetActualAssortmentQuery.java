package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.GetSKUContextConverter;
import ru.a5x5retail.frontproductmanagement.db.converters.loSkuContextConverter;
import ru.a5x5retail.frontproductmanagement.db.models.SKUContext;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db_local.loSkuContext;


public class GetActualAssortmentQuery extends CallableQAsync {

    private Date date;
    private List <loSkuContext> list;

    public GetActualAssortmentQuery(Date date) {

        this.date = date;
        list = new ArrayList<>();
    }


    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.GetActualAssortment (?)");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            java.sql.Date d = new java.sql.Date(date.getTime());
            getStmt().setDate(parameterIndex++, d);
    }

    @Override
    protected void parseResultSet() throws SQLException {
        loSkuContextConverter converter = new loSkuContextConverter();
            while (getResultSet().next()) {
                loSkuContext head = new loSkuContext();
                converter.Convert(getResultSet(), head);
                list.add(head);
            }
    }

    public List<loSkuContext> getList() {
        return list;
    }
}
