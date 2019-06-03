package ru.a5x5retail.frontproductmanagement.db.query.update;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.models.AcceptResult;
import ru.a5x5retail.frontproductmanagement.db.models.AcceptValidateMessage;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class ValidationAndAcceptInvoiceQuery extends CallableQAsync {

    /*
    *
    *
    *    @CheckingListIncHeadGUID uniqueidentifier
        ,@PositionGUID uniqueidentifier
        ,@Qty money
        ,@OperationType int
    *
    * */



    private String objectGuid,eventMessage;
    private int acceptingMode, isValidated,isAccepted;

    private AcceptResult acceptResult;


    public ValidationAndAcceptInvoiceQuery(String objectGuid) {

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

   /* @Override
    protected void Execute() {
        super.Execute();

        try {
            boolean b = getStmt().execute();
            setResultSet(getStmt().getResultSet());
            acceptResult = new AcceptResult();
            if (getResultSet() != null) {
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
                acceptResult.acceptValidateMessageList = acceptValidateMessageList;
            }

            setReturnCode(getStmt().getInt(1));
            acceptResult.returnCode = getReturnCode();
            acceptResult.isValidated = getStmt().getInt(4);
            acceptResult.isAccepted = getStmt().getInt(5);
            acceptResult.eventMessage = getStmt().getString(6);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    public AcceptResult getAcceptResult() {
        if (acceptResult == null) {
            acceptResult = new AcceptResult();
        }
        return acceptResult;
    }
}
