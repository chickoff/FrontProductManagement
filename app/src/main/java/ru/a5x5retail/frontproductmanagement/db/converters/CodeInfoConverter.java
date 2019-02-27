package ru.a5x5retail.frontproductmanagement.db.converters;

import java.sql.ResultSet;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorInfo;
import ru.a5x5retail.frontproductmanagement.interfaces.IDbConvertible;

public class CodeInfoConverter implements IDbConvertible<CodeInfo> {
    @Override
    public boolean Convert(ResultSet resultSet, CodeInfo obj) throws SQLException {
        obj.code = resultSet.getInt("code");
        obj.nameLong = resultSet.getString("nameLong");
        return false;
    }




/*    @Override
    public boolean Convert(ResultSet resultSet, SubGroupInfo obj) throws SQLException {
        try {
            obj.setGroup5Guid(resultSet.getObject("group5Guid").toString());
            obj.setFullName(resultSet.getObject("nameLong").toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }*/




   /* @Override
    public boolean Convert(ResultSet resultSet, InventoryList obj) {
        try {
            obj.inventoryGuid =  resultSet.getObject("inventoryGuid").toString();
            obj.nameLong =  resultSet.getObject("nameLong").toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }*/
}
