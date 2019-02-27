package ru.a5x5retail.frontproductmanagement.db.converters;

import ru.a5x5retail.frontproductmanagement.db.models.InventoryList;
import ru.a5x5retail.frontproductmanagement.interfaces.IDbConvertible;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryListConverter implements IDbConvertible<InventoryList> {
    @Override
    public boolean Convert(ResultSet resultSet, InventoryList obj) {
        try {
            obj.inventoryGuid =  resultSet.getObject("inventoryGuid").toString();
            obj.nameLong =  resultSet.getObject("nameLong").toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
