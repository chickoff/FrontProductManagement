package ru.a5x5retail.frontproductmanagement.db.query.update.async;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.models.AcceptResult;
import ru.a5x5retail.frontproductmanagement.db.models.AcceptValidateMessage;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorExtendedInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQueryAsync;

public class ValidationAndAcceptInvoiceQueryAsync extends CallableQAsync {

    /*
    *
    *
    *    @CheckingListIncHeadGUID uniqueidentifier
        ,@PositionGUID uniqueidentifier
        ,@Qty money
        ,@OperationType int
    *
    * */



    private String objectGuid;
    private AcceptResult acceptResult;

    public ValidationAndAcceptInvoiceQueryAsync(String objectGuid) {
        this.objectGuid = objectGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.CheckingListIncInvoiceValidationAndAccept (?, ?, ?, ?, ?)}");
    }

    @Override
    protected void SetQueryParams() {

        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++,objectGuid);
            getStmt().setInt(parameterIndex++, 1);
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().registerOutParameter(parameterIndex++, Types.OTHER);
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }

    @Override
    protected void parseResultSet() throws Exception {
        List<AcceptValidateMessage> acceptValidateMessageList = new ArrayList<>();
        while (getResultSet().next()) {
            AcceptValidateMessage acceptValidateMessage = new AcceptValidateMessage();
            acceptValidateMessage.idd = getResultSet().getInt("IDD");
            acceptValidateMessage.text = getResultSet().getString("text");
            acceptValidateMessage.note = getResultSet().getString("note");
            acceptValidateMessage.level = getResultSet().getInt("level");
            acceptValidateMessage.orderBy = getResultSet().getInt("orderBy");
            acceptValidateMessage.spName = getResultSet().getString("spName");
            acceptValidateMessage.dateCreate = getResultSet().getDate("dateCreate");
            acceptValidateMessageList.add(acceptValidateMessage);
        }
        getAcceptResult().acceptValidateMessageList = acceptValidateMessageList;
    }

    @Override
    protected void parseOutputVars() throws SQLException {

        getAcceptResult().returnCode = getReturnCode();
        getAcceptResult().isValidated = getStmt().getInt(4);
        getAcceptResult().isAccepted = getStmt().getInt(5);
        eventMessage = getStmt().getString(6);
        getAcceptResult().eventMessage = eventMessage;

        returnCode = acceptResult.returnCode;
        eventMessage = acceptResult.eventMessage;
    }

    public AcceptResult getAcceptResult() {
        if (acceptResult == null) {
            acceptResult = new AcceptResult();
        }
        return acceptResult;
    }
}
