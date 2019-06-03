package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.InventoryListConverter;
import ru.a5x5retail.frontproductmanagement.db.models.InventoryList;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;
import ru.a5x5retail.frontproductmanagement.interfaces.IQuery;

public class GetLocalInventoryListQuery extends CallableQAsync {
    private List<InventoryList> inventoryList;
    private int typeDocId;
    public GetLocalInventoryListQuery(int typeDocId) {
        inventoryList = new ArrayList<>();
        this.typeDocId = typeDocId;
    }


    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.InventoryLocalGetList (?)}");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        parameterIndex = 1;
        getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
        getStmt().setInt(parameterIndex++,typeDocId);


    }

/*    @Override
    protected void Execute() {
        super.Execute();
        try {
            boolean b = getStmt().execute();
            setResultSet(getStmt().getResultSet());
            InventoryListConverter converter = new InventoryListConverter();
            while (getResultSet().next()) {
                InventoryList inv = new InventoryList();
                converter.Convert(getResultSet(), inv);
                inventoryList.add(inv);
            }
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }*/

    @Override
    protected void parseResultSet() throws SQLException {
            InventoryListConverter converter = new InventoryListConverter();
            while (getResultSet().next()) {
                InventoryList inv = new InventoryList();
                converter.Convert(getResultSet(), inv);
                inventoryList.add(inv);
            }
    }

    public List<InventoryList> getInventoryList() {
        return inventoryList;
    }
}
