package ru.a5x5retail.frontproductmanagement.db.query.read;

import android.os.Debug;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.CodeInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.converters.InvoiceHeadConverter;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.db.models.InvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetCodeBySubgroupQuery extends CallableQuery<CodeInfo> {

    private String xml;

    private List<CodeInfo> list;

    public GetCodeBySubgroupQuery(Connection connection, String xml) {
        super(connection);
        list = new ArrayList<>();
        this.xml = xml;
    }

    @Override
    protected void SetQuery() {
        setSqlString("call V_StoreTSD.dbo.GetSkuBySubgroup (?)");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.setString(1,xml);
    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        SetQuery();
        createStatement();
        SetQueryParams();
        setResultSet(stmt.executeQuery());
        CodeInfoConverter converter = new CodeInfoConverter();
        while (getResultSet().next()) {
            CodeInfo head = new CodeInfo();
            converter.Convert(getResultSet(),head);
            list.add(head);
        }
    }

    public List<CodeInfo> getList() {
        return list;
    }
}
