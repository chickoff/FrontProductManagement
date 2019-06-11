package ru.a5x5retail.frontproductmanagement.db.converters;

import java.sql.ResultSet;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.db.models.InventoryCode;
import ru.a5x5retail.frontproductmanagement.db.models.SKUContext;
import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IDbConvertible;


public class InventoryCodeConverter implements IDbConvertible<InventoryCode> {

    @Override
    public boolean Convert(ResultSet resultSet, InventoryCode obj) {

        try {

            obj.code = resultSet.getInt("Code");
            obj.nameLong = resultSet.getString("NameLong");
            obj.qty = resultSet.getBigDecimal("Qty");
            obj.measureUnitIDD = resultSet.getInt("MeasureUnitIDD");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
