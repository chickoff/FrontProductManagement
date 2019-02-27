package ru.a5x5retail.frontproductmanagement.db.query.read;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.SubGroupInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.models.SubGroupInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.SkuGroup;

public class GetSkuAllGroupsXmlQuery extends CallableQuery<SubGroupInfo> {
    private SkuGroup group;
    public GetSkuAllGroupsXmlQuery(Connection connection) {
        super(connection);
    }

    @Override
    protected void SetQuery() {
        setSqlString("call V_StoreTSD.dbo.GetSkuAllGroups");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        while (getResultSet().next()) {
            String s = getResultSet().getString("Xml");
            Serializer serializer = new Persister();
            try {
                group = serializer.read(SkuGroup.class, s);
                group.SetParent(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public SkuGroup getRootGroup() {
        return group;
    }
}
