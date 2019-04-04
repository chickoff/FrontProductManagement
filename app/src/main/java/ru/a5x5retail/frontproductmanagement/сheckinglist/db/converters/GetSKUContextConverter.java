package ru.a5x5retail.frontproductmanagement.сheckinglist.db.converters;

import java.sql.ResultSet;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.db.models.SKUContext;
import ru.a5x5retail.frontproductmanagement.сheckinglist.interfaces.IDbConvertible;

public class GetSKUContextConverter implements IDbConvertible<SKUContext> {

    @Override
    public boolean Convert(ResultSet resultSet, SKUContext obj) {

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
