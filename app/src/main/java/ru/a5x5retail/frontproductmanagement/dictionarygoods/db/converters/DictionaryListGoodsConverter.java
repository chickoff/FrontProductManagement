package ru.a5x5retail.frontproductmanagement.dictionarygoods.db.converters;

import java.sql.ResultSet;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.dictionarygoods.db.models.DictionaryListGoods;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.interfaces.IDbConvertible;

public class DictionaryListGoodsConverter implements IDbConvertible<DictionaryListGoods> {
    @Override
    public boolean Convert(ResultSet resultSet, DictionaryListGoods obj) {

        try {


            obj.Code = resultSet.getInt("Code");
            obj.NameLong = resultSet.getString("NameLong");
            obj.Check = resultSet.getBoolean("Check");



        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }
}
