package ru.a5x5retail.frontproductmanagement.сheckinglist.db.converters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import ru.a5x5retail.frontproductmanagement.сheckinglist.db.models.CheckingListGoods;
import ru.a5x5retail.frontproductmanagement.сheckinglist.interfaces.IDbConvertible;

public class CheckingListGoodsConverter implements IDbConvertible<CheckingListGoods> {
    @Override
    public boolean Convert(ResultSet resultSet, CheckingListGoods obj) {

      //  UUID;
        try {

            obj.Guid = UUID.fromString(resultSet.getObject("GUID").toString());
            obj.CheckingListHeadGuid = UUID.fromString(resultSet.getObject("CheckingListHeadGuid").toString());
            obj.Code = resultSet.getInt("Code");
            obj.NameLong = resultSet.getString("NameLong");
            obj.MeasureUnitIDD = resultSet.getInt("MeasureUnitIDD");
            obj.Check = resultSet.getBoolean("Check");
            obj.Qty = resultSet.getBigDecimal("Qty");
            obj.OrderBy = resultSet.getInt("OrderBy");


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
