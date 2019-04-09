package ru.a5x5retail.frontproductmanagement.db.converters;

import java.sql.ResultSet;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.db.models.IncomeInvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.models.OutgoInvoiceHead;
import ru.a5x5retail.frontproductmanagement.interfaces.IDbConvertible;

public class OutgoInvoiceHeadConverter implements IDbConvertible<OutgoInvoiceHead> {
    @Override
    public boolean Convert(ResultSet resultSet, OutgoInvoiceHead obj) throws SQLException {
        obj.guid = resultSet.getObject("GUID").toString();
        obj.numDoc = resultSet.getString("NumDoc");
        obj.dateDoc = resultSet.getDate("DateDoc");
        obj.summ = resultSet.getBigDecimal("Summ");
        obj.contractorNameLong = resultSet.getString("ContractorNameLong");
        return true;
    }
}



