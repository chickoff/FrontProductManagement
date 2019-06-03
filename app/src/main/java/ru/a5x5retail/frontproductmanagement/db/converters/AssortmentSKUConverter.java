package ru.a5x5retail.frontproductmanagement.db.converters;

import java.sql.ResultSet;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.db.models.AssortmentSku;
import ru.a5x5retail.frontproductmanagement.db.models.SKUContext;
import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IDbConvertible;


public class AssortmentSKUConverter implements IDbConvertible<AssortmentSku> {

    @Override
    public boolean Convert(ResultSet resultSet, AssortmentSku obj) {
        try {
                obj.code = resultSet.getInt("Code");
                obj.nameLong = resultSet.getString("NameLong");
                obj.measureUnitIdd = resultSet.getString("MeasureUnitIDD");
                obj.qtyNow = resultSet.getBigDecimal("qtyNow");
                obj.priceRegular = resultSet.getBigDecimal("priceRegular");
                obj.priceCash = resultSet.getBigDecimal("priceCash");

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

}
