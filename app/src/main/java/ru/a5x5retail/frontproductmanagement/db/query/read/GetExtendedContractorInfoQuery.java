package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.db.models.ContractorExtendedInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetExtendedContractorInfoQuery extends CallableQuery<ContractorExtendedInfo> {

    private String contractorGuid;

    public GetExtendedContractorInfoQuery(Connection connection, String contractorGuid) {
        super(connection);
        this.contractorGuid = contractorGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("call V_StoreTSD.dbo.GetContractorsExtended ?");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
        stmt.setString(1,contractorGuid);
    }

    private ContractorExtendedInfo contractorExtendedInfo;

    @Override
    public void Execute() throws SQLException {
        super.Execute();

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

    }

    public ContractorExtendedInfo getContractorExtendedInfo() {
        return contractorExtendedInfo;
    }
}
