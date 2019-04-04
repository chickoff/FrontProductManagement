package ru.a5x5retail.frontproductmanagement.сheckinglist.db.converters;

import java.sql.ResultSet;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.сheckinglist.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.сheckinglist.interfaces.IDbConvertible;

public class CheckingListHeadConverter implements IDbConvertible<CheckingListHead> {
    @Override
    public boolean Convert(ResultSet resultSet, CheckingListHead obj) {
        try {
            obj.Guid = resultSet.getObject("GUID").toString();
            obj.StatusID = resultSet.getObject("StatusID").toString();
            obj.TypeDocID = resultSet.getObject("TypeDocID").toString();
            obj.RRGUID = resultSet.getObject("sourceGuid").toString();
            obj.NameDoc = resultSet.getObject("NameDoc").toString();
            obj.Note = resultSet.getString("Note");
            obj.IMEI = resultSet.getObject("IMEI").toString();
            obj.LDM = resultSet.getObject("LDM").toString();
            obj.LDC = resultSet.getObject("LDC").toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
