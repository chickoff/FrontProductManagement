package ru.a5x5retail.frontproductmanagement.db.converters;

import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.interfaces.IDbConvertible;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckingListHeadConverter implements IDbConvertible<CheckingListHead> {
    @Override
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
            obj.errorCode = resultSet.getInt("ErrorCode");
            obj.errorMessage = resultSet.getObject("ErrorMessage").toString();
            obj.summ = resultSet.getBigDecimal("Summ");
            obj.contractorName = resultSet.getString("ContractorName");
            obj.summVat = resultSet.getBigDecimal("SummVat");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
