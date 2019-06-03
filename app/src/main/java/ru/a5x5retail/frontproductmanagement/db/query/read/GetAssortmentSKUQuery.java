package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.AssortmentSKUConverter;
import ru.a5x5retail.frontproductmanagement.db.converters.GetSKUContextConverter;
import ru.a5x5retail.frontproductmanagement.db.models.AssortmentSku;
import ru.a5x5retail.frontproductmanagement.db.models.SKUContext;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;


public class GetAssortmentSKUQuery extends CallableQAsync {

    private String source;
    private List <AssortmentSku> list;

    public GetAssortmentSKUQuery(String source) {

        this.source = source;
        list = new ArrayList<>();
    }


    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.GetSKUInfo (?,?)");
    }

    @Override
    protected void SetQueryParams() {
        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++, source);
            getStmt().registerOutParameter(parameterIndex++, Types.OTHER);
        } catch (Exception e) {
            e.printStackTrace();
            setException(e);
        }
    }

    /*@Override
    protected void Execute() {
        super.Execute();

        try {
            boolean b = getStmt().execute();
            setResultSet(getStmt().getResultSet());
            AssortmentSKUConverter converter = new AssortmentSKUConverter();
            if (getResultSet() != null) {
                while (getResultSet().next()) {
                    AssortmentSku head = new AssortmentSku();
                    converter.Convert(getResultSet(), head);
                    list.add(head);
                }
            }
            setReturnCode((int) getStmt().getObject(1));
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }*/

    @Override
    protected void parseOutputVars() throws SQLException {
        setEventMessage(getStmt().getString(3));
    }

    @Override
    protected void parseResultSet() throws SQLException {
        AssortmentSKUConverter converter = new AssortmentSKUConverter();
        while (getResultSet().next()) {
            AssortmentSku head = new AssortmentSku();
            converter.Convert(getResultSet(), head);
            list.add(head);
        }
    }



    public List<AssortmentSku> getList() {
        return list;
    }
}
