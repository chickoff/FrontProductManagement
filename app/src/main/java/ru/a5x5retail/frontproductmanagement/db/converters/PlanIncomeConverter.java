package ru.a5x5retail.frontproductmanagement.db.converters;

import java.sql.ResultSet;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.db.models.PlanIncome;
import ru.a5x5retail.frontproductmanagement.interfaces.IDbConvertible;

public class PlanIncomeConverter implements IDbConvertible<PlanIncome> {
    @Override
    public boolean Convert(ResultSet resultSet, PlanIncome obj) throws SQLException {
        obj.planIncomeGuid = resultSet.getObject("PlanIncomeGUID").toString();
        obj.numDoc = resultSet.getObject("NumDoc").toString();
        obj.dateDoc = resultSet.getObject("DateDoc").toString();
        obj.qty = resultSet.getObject("Qty").toString();
        obj.validDoc = resultSet.getInt("ValidDoc");
        return true;







    }
}
