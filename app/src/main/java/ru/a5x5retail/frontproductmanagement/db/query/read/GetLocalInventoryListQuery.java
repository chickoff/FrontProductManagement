package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.InventoryListConverter;
import ru.a5x5retail.frontproductmanagement.db.models.InventoryList;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;
import ru.a5x5retail.frontproductmanagement.interfaces.IQuery;

public class GetLocalInventoryListQuery extends CallableQuery<InventoryList> {
    private List<InventoryList> inventoryList;
    public GetLocalInventoryListQuery(Connection connection) {
        super(connection);
        inventoryList = new ArrayList<>();
    }

    @Override
    protected void SetQuery() {
        setSqlString("{call V_StoreTSD.dbo.InventoryLocalGetList}");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        //none
    }

    @Override
    public void Execute() throws SQLException {
        super.Execute();
        InventoryListConverter converter = new InventoryListConverter();
        while (getResultSet().next()) {
            InventoryList inv = new InventoryList();
            converter.Convert(getResultSet(),inv);
            inventoryList.add(inv);
        }
    }

    public List<InventoryList> getInventoryList() {
        return inventoryList;
    }
}
