package ru.a5x5retail.frontproductmanagement.db.converters;

import java.sql.ResultSet;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListMark;
import ru.a5x5retail.frontproductmanagement.interfaces.IDbConvertible;

public class CheckingListMarkConverter implements IDbConvertible<CheckingListMark> {
    @Override
    public boolean Convert(ResultSet resultSet, CheckingListMark obj) throws SQLException {
        obj.guid = resultSet.getObject("GUID").toString();
       // obj.checkingListIncHeadGuid = resultSet.getObject("checkingListIncHeadGuid").toString();
        obj.code = resultSet.getInt("code");
        obj.qtyCl = resultSet.getBigDecimal("qtyCl").setScale(3);
        obj.qtyTtn = resultSet.getBigDecimal("qtyTtn").setScale(3);
        obj.orderBy = resultSet.getInt("orderBy");
        return false;
    }


/*    @Override
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
