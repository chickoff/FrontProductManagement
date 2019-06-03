package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.db.models.ContractorExtendedInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetExtendedContractorInfoQuery extends CallableQAsync {

    private String contractorGuid;

    public GetExtendedContractorInfoQuery(String contractorGuid) {

        this.contractorGuid = contractorGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.GetContractorsExtended ?");
    }

    @Override
    protected void SetQueryParams() {
        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++,contractorGuid);
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }

    private ContractorExtendedInfo contractorExtendedInfo;

/*    @Override
    protected void Execute()  {
        super.Execute();
        try {
            boolean b = getStmt().execute();
            setResultSet(getStmt().getResultSet());
            ContractorExtendedInfo info = new ContractorExtendedInfo();
            ResultSet resultSet = getResultSet();
            resultSet.next();
            info.contractorGuid = resultSet.getString("ContractorGUID");
            info.contractorName = resultSet.getString("ContractorName");
            info.agreementGuid = resultSet.getString("AgreementGUID");
            info.edi = resultSet.getInt("EDI");
            info.ediTp = resultSet.getInt("EDITP");
            info.rpbpp = resultSet.getInt("RPbPP");
            info.cz = resultSet.getInt("CZ");
            contractorExtendedInfo = info;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Override
    protected void parseResultSet() throws SQLException {
            ContractorExtendedInfo info = new ContractorExtendedInfo();
            getResultSet().next();
            info.contractorGuid = getResultSet().getString("ContractorGUID");
            info.contractorName = getResultSet().getString("ContractorName");
            info.agreementGuid = getResultSet().getString("AgreementGUID");
            info.edi = getResultSet().getInt("EDI");
            info.ediTp = getResultSet().getInt("EDITP");
            info.rpbpp = getResultSet().getInt("RPbPP");
            info.cz = getResultSet().getInt("CZ");
            contractorExtendedInfo = info;
    }

    public ContractorExtendedInfo getContractorExtendedInfo() {
        return contractorExtendedInfo;
    }
}
