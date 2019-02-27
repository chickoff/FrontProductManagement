package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.GetSkuChildGroupByConverter;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.SkuGroup;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetSkuChildGroupByQuery extends CallableQuery<SkuGroup> {

    int layer, numLayer, groupCode;
    private List<SkuGroup> list;
    public GetSkuChildGroupByQuery(Connection connection,int layer ,int numLayer,int groupCode) {
        super(connection);
        list = new ArrayList<>();
        this.layer = layer;
        this.numLayer = numLayer;
        this.groupCode = groupCode;
    }

    @Override
    protected void SetQuery() {
        setSqlString("call V_StoreTSD.dbo.GetSkuChildGroupBy (?,?,?)");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.setInt(1,layer);
        stmt.setInt(2,numLayer);
        stmt.setInt(3,groupCode);
    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        GetSkuChildGroupByConverter converter = new GetSkuChildGroupByConverter();
        while (getResultSet().next()) {
            SkuGroup info = new SkuGroup();
            converter.Convert(getResultSet(),info);
            list.add(info);
        }
    }

    public List<SkuGroup> getList() {
        return list;
    }
}
