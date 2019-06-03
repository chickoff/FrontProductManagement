package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.ContractorInfoConverter;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetAllContractorsQuery extends CallableQAsync {

    private String checkingListGuid;

    private List<ContractorInfo> list;

    public GetAllContractorsQuery() {

        list = new ArrayList<>();
        this.checkingListGuid = checkingListGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.GetContractorsAll ");
    }

    @Override
    protected void SetQueryParams() {
        parameterIndex = 1;
        try {
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*@Override
    protected void Execute() {
        super.Execute();

        try {
            boolean b = getStmt().execute();
            setResultSet(getStmt().getResultSet());
            ContractorInfoConverter converter = new ContractorInfoConverter();
            while (getResultSet().next()) {
                ContractorInfo head = new ContractorInfo();
                converter.Convert(getResultSet(), head);
                list.add(head);
            }
            setReturnCode((int) getStmt().getObject(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Override
    protected void parseResultSet() throws SQLException {
            ContractorInfoConverter converter = new ContractorInfoConverter();
            while (getResultSet().next()) {
                ContractorInfo head = new ContractorInfo();
                converter.Convert(getResultSet(), head);
                list.add(head);
            }
    }

    public List<ContractorInfo> getList() {
        return list;
    }
}
