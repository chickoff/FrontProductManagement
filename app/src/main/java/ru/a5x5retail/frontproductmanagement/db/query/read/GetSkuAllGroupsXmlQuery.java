package ru.a5x5retail.frontproductmanagement.db.query.read;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.SubGroupInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.models.SubGroupInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.SkuGroup;

public class GetSkuAllGroupsXmlQuery extends CallableQAsync {
    private SkuGroup group;
    public GetSkuAllGroupsXmlQuery() {

    }

    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.GetSkuAllGroups }");
    }

    @Override
    protected void SetQueryParams()  {
        parameterIndex = 1;
        try {
            getStmt().registerOutParameter(parameterIndex++, Types.OTHER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

/*    @Override
    protected void Execute()  {
        super.Execute();
        try {
            boolean b = getStmt().execute();
            setResultSet(getStmt().getResultSet());

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
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }*/

    @Override
    protected void parseResultSet() throws Exception {
        getResultSet().next();
        String groupXml = getResultSet().getString("xml");
        Serializer serializer = new Persister();
        group = serializer.read(SkuGroup.class, groupXml);
        group.SetParent(null);
    }

    @Override
    protected void parseOutputVars() throws Exception {

        /*Blob blob = getStmt().getBlob(2);
        long ddd = blob.length();
        ddd = ddd;







        String groupXml = getStmt().getString(2);
        byte[] b = groupXml.getBytes();
        String s = new String( b, StandardCharsets.UTF_8);
        s = s;
        Serializer serializer = new Persister();
        group = serializer.read(SkuGroup.class, groupXml);
        group.SetParent(null);*/
    }

    public SkuGroup getRootGroup() {
        return group;
    }
}
