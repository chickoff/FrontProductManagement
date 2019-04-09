package ru.a5x5retail.frontproductmanagement.db.converters;

import java.sql.ResultSet;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.db.models.CheckingListControlQty;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListMark;
import ru.a5x5retail.frontproductmanagement.interfaces.IDbConvertible;

public class CheckingListControlQtyConverter implements IDbConvertible<CheckingListControlQty> {
    @Override
    public boolean Convert(ResultSet resultSet, CheckingListControlQty obj) throws SQLException {
        obj.сheckingListIncGuid = resultSet.getObject("CheckingListIncGUID").toString();
        obj.sourceGuid = resultSet.getObject("SourceGUID").toString();
        obj.code = resultSet.getInt("Code");
        obj.qty = resultSet.getBigDecimal("Qty").setScale(3);
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
