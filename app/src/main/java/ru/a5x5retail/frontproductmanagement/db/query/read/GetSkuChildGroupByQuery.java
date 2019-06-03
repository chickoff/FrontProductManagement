package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.GetSkuChildGroupByConverter;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.SkuGroup;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetSkuChildGroupByQuery extends CallableQAsync {

    int layer, numLayer, groupCode;
    private List<SkuGroup> list;
    public GetSkuChildGroupByQuery(int layer ,int numLayer,int groupCode) {

        list = new ArrayList<>();
        this.layer = layer;
        this.numLayer = numLayer;
        this.groupCode = groupCode;
    }

    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.GetSkuChildGroupBy (?,?,?)}");
    }

    @Override
    protected void SetQueryParams() {
        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setInt(parameterIndex++,layer);
            getStmt().setInt(parameterIndex++,numLayer);
            getStmt().setInt(parameterIndex++,groupCode);
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }

    }


    @Override
    protected void parseResultSet() throws SQLException {
            GetSkuChildGroupByConverter converter = new GetSkuChildGroupByConverter();
            while (getResultSet().next()) {
                SkuGroup info = new SkuGroup();
                converter.Convert(getResultSet(), info);
                list.add(info);
            }
    }

    public List<SkuGroup> getList() {
        return list;
    }
}
