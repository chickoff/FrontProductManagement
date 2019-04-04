package ru.a5x5retail.frontproductmanagement.db.converters;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;
import ru.a5x5retail.frontproductmanagement.interfaces.IDbConvertible;

public class CheckingListPositionConverter implements IDbConvertible<CheckingListPosition> {
    @Override
    public boolean Convert(ResultSet resultSet, CheckingListPosition obj) throws SQLException {
        obj.guid = resultSet.getObject("GUID").toString();
        obj.checkingListIncHeadGuid = resultSet.getObject("checkingListIncHeadGuid").toString();
        obj.code = resultSet.getInt("code");
        obj.qtyUser = resultSet.getBigDecimal("qtyUser").setScale(3);
        obj.orderBy = resultSet.getInt("orderBy");

        BigDecimal incomeGoodsQty = resultSet.getBigDecimal("incomeGoodsQty");
        obj.incomeGoodsQty = (incomeGoodsQty == null ? new BigDecimal(0):incomeGoodsQty).setScale(3);

        BigDecimal price = resultSet.getBigDecimal("price");
        obj.price = (price == null ? new BigDecimal(0):price).setScale(2);

        obj.vat = resultSet.getInt("vat");
        obj.manufacturingDate = resultSet.getDate("manufacturingDate");
        obj.nameLong = resultSet.getString("nameLong");
        obj.measureUnitIdd = resultSet.getInt("measureUnitIdd");
        obj.manufacturingDateFl = resultSet.getInt("manufacturingDateFl");
        obj.validEror = resultSet.getInt("validEror");
        return false;
    }


    /*@Override
    public boolean Convert(ResultSet resultSet, CheckingListHead obj) {
        try {
            obj.Guid = resultSet.getObject("GUID").toString();
            obj.StatusID = resultSet.getObject("StatusID").toString();
            obj.TypeDocID = resultSet.getObject("TypeDocID").toString();
            obj.sourceGuid = resultSet.getObject("sourceGuid").toString();
            obj.NameDoc = resultSet.getObject("NameDoc").toString();
            obj.Note = resultSet.getString("Note");
            obj.IMEI = resultSet.getObject("IMEI").toString();
            obj.LDM = resultSet.getObject("LDM").toString();
            obj.LDC = resultSet.getObject("LDC").toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }*/


}
