package ru.a5x5retail.frontproductmanagement.db.converters;

import java.sql.ResultSet;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.db.models.SKUContext;
import ru.a5x5retail.frontproductmanagement.db_local.loSkuContext;
import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IDbConvertible;


public class loSkuContextConverter implements IDbConvertible<loSkuContext> {

    @Override
    public boolean Convert(ResultSet resultSet, loSkuContext obj) {

        try {

            obj.Code = resultSet.getInt("Code");
            obj.NameLong = resultSet.getString("NameLong");
            obj.MeasureUnitIDD = resultSet.getInt("MeasureUnitIDD");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
