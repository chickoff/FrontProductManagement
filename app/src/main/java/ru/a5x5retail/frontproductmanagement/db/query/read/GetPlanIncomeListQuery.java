package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.Version;
import ru.a5x5retail.frontproductmanagement.db.converters.PlanIncomeConverter;
import ru.a5x5retail.frontproductmanagement.db.models.PlanIncome;
import ru.a5x5retail.frontproductmanagement.db.query.PreparedQuery;

public class GetPlanIncomeListQuery extends PreparedQuery {  
    String contractorGuid;

    public GetPlanIncomeListQuery(Connection connection,String contractorGuid) {
        super(connection);
        this.contractorGuid = contractorGuid;
        planIncomeList = new ArrayList<>();
    }

    @Override
    protected void SetQuery() {
        setSqlString("select * from V_StoreTSD.dbo.GetPlanIncomeList (?, ?, ?)");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.setObject(1, null);
        stmt.setString(2, contractorGuid);
        stmt.setObject(3, null);

    }

    public List<PlanIncome> getPlanIncomeList() {
        return planIncomeList;
    }

    public void setPlanIncomeList(List<PlanIncome> planIncomeList) {
        this.planIncomeList = planIncomeList;
    }

    private List<PlanIncome> planIncomeList;

    @Override
    public void Execute() throws SQLException {
       super.Execute();
        PlanIncomeConverter planIncomeConverter = new PlanIncomeConverter();
        PlanIncome planIncome;

        while (getResultSet().next()) {
            planIncome = new PlanIncome();
            planIncomeConverter.Convert(getResultSet(),planIncome);
            planIncomeList.add(planIncome);
        }
    }   
}
