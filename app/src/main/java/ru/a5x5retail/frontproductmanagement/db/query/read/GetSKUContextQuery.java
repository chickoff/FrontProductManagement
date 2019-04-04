package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.CodeInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.converters.GetSKUContextConverter;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.db.models.SKUContext;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;


public class GetSKUContextQuery extends CallableQuery<SKUContext> {

    private String barcode;
    private List <SKUContext> list;

    public GetSKUContextQuery(Connection connection, String barcode) {
        super(connection);
        this.barcode = barcode;
        list = new ArrayList<>();
    }


    @Override
    protected void SetQuery() {
        setSqlString("call V_StoreTSD.dbo.GetSKUContext (?)");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.setString(1,barcode);
    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        GetSKUContextConverter converter = new GetSKUContextConverter();

        while (getResultSet().next()) {
            SKUContext head = new SKUContext();
            converter.Convert(getResultSet(),head);
            list.add(head);
        }
    }

    public List<SKUContext> getList() {
        return list;
    }
}
