package ru.a5x5retail.frontproductmanagement.printprice.db.converters;

import java.sql.ResultSet;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.printprice.db.models.PrintPriceItem;
import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IDbConvertible;

public class PrintPriceItemConverter implements IDbConvertible<PrintPriceItem> {
    @Override
    public boolean Convert(ResultSet resultSet, PrintPriceItem obj) {

        try {

            obj.Guid = resultSet.getObject("GUID").toString();
            obj.HeaderGuid =resultSet.getObject("CheckingListHeadGuid").toString();
            obj.Code = resultSet.getInt("Code");
            obj.NameLong = resultSet.getString("NameLong");
            obj.MeasureUnitIDD = resultSet.getInt("MeasureUnitIDD");
            obj.Check = resultSet.getBoolean("Check");
            obj.Qty = resultSet.getInt("Qty");
            obj.OrderBy = resultSet.getInt("OrderBy");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

