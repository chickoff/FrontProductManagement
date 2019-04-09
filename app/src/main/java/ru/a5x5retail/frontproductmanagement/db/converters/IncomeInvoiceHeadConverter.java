package ru.a5x5retail.frontproductmanagement.db.converters;

import java.sql.ResultSet;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.db.models.IncomeInvoiceHead;
import ru.a5x5retail.frontproductmanagement.interfaces.IDbConvertible;

public class IncomeInvoiceHeadConverter implements IDbConvertible<IncomeInvoiceHead> {
    @Override
    public boolean Convert(ResultSet resultSet, IncomeInvoiceHead obj) throws SQLException {

        Object tmp = null;

        obj.guid = resultSet.getObject("SourceGUID").toString();
        obj.numDoc = resultSet.getString("NumDoc");
        obj.dateDoc = resultSet.getDate("DateDoc");
        tmp = null;
        tmp = resultSet.getObject("Summ"); obj.summ = tmp == null ? "0" : tmp.toString();
        tmp = null;
        tmp = resultSet.getObject("SummVat"); obj.summVat = tmp == null ? "0" : tmp.toString();
        obj.validDoc = resultSet.getInt("ValidDoc");
        obj.sourceTypeIdd = resultSet.getInt("SourceTypeIDD");

        return true;
    }
}



