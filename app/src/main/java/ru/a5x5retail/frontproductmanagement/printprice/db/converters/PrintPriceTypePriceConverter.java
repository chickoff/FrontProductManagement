package ru.a5x5retail.frontproductmanagement.printprice.db.converters;

import java.sql.ResultSet;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.printprice.db.models.PrintPriceTypePrice;
import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IDbConvertible;

public class PrintPriceTypePriceConverter implements IDbConvertible<PrintPriceTypePrice> {
    @Override
    public boolean Convert(ResultSet resultSet, PrintPriceTypePrice obj) {

        try {


            obj.Id = resultSet.getInt("ID");
            obj.NameLong = resultSet.getString("NameLong");


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

