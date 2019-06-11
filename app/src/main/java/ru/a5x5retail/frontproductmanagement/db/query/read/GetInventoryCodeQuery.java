package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.GetSKUContextConverter;
import ru.a5x5retail.frontproductmanagement.db.converters.InventoryCodeConverter;
import ru.a5x5retail.frontproductmanagement.db.models.InventoryCode;
import ru.a5x5retail.frontproductmanagement.db.models.SKUContext;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;


public class GetInventoryCodeQuery extends CallableQAsync {

    private String inventoryGuid;
    private List <InventoryCode> list;

    public GetInventoryCodeQuery(String inventoryGuid) {

        this.inventoryGuid = inventoryGuid;
        list = new ArrayList<>();
    }


    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.InventoryLocalGetSharedInvSheet (?)");
    }

    @Override
    protected void SetQueryParams() throws SQLException {

            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++,inventoryGuid);
    }

    @Override
    protected void parseResultSet() throws SQLException {
        InventoryCodeConverter converter = new InventoryCodeConverter();
            while (getResultSet().next()) {
                InventoryCode head = new InventoryCode();
                converter.Convert(getResultSet(), head);
                list.add(head);
            }
    }

    public List<InventoryCode> getList() {
        return list;
    }
}
