package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.Version;
import ru.a5x5retail.frontproductmanagement.db.converters.PlanIncomeConverter;
import ru.a5x5retail.frontproductmanagement.db.models.PlanIncome;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.PreparedQuery;

public class GetPlanIncomeListQuery extends CallableQAsync {
    String contractorGuid;

    public GetPlanIncomeListQuery(String contractorGuid) {

        this.contractorGuid = contractorGuid;
        planIncomeList = new ArrayList<>();
    }

    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.CheckingListIncGetListPlanIncome ?}");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
            parameterIndex = 1;
        getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
        getStmt().setString(parameterIndex, contractorGuid);
    }

    public List<PlanIncome> getPlanIncomeList() {
        return planIncomeList;
    }

    public void setPlanIncomeList(List<PlanIncome> planIncomeList) {
        this.planIncomeList = planIncomeList;
    }

    private List<PlanIncome> planIncomeList;

    @Override
    protected void parseResultSet() throws Exception {
        PlanIncomeConverter planIncomeConverter = new PlanIncomeConverter();
        while (getResultSet().next()) {
            PlanIncome planIncome = new PlanIncome();
            planIncomeConverter.Convert(getResultSet(), planIncome);
            planIncomeList.add(planIncome);
        }
    }
}
