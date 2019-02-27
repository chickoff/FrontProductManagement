package ru.a5x5retail.frontproductmanagement.db.converters;

import java.sql.ResultSet;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.db.models.InvoiceHead;
import ru.a5x5retail.frontproductmanagement.interfaces.IDbConvertible;

public class InvoiceHeadConverter implements IDbConvertible<InvoiceHead> {
    @Override
    public boolean Convert(ResultSet resultSet, InvoiceHead obj) throws SQLException {

        Object tmp = null;

        obj.guid = resultSet.getObject("GUID").toString();
        obj.numDoc = resultSet.getString("NumDoc");
        obj.dateDoc = resultSet.getDate("DateDoc");
        obj.conractorNameLong = resultSet.getString("ContractorNameLong");

        tmp = null;
        tmp = resultSet.getObject("Summ");
        obj.summ = tmp == null ? "0" : tmp.toString();
        return false;
    }
}
