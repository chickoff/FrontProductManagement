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
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class ValidationAndAcceptInvoiceQuery extends CallableQuery {

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


    public ValidationAndAcceptInvoiceQuery(Connection connection, String objectGuid) {
        super(connection);
        this.objectGuid = objectGuid;
    }

    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.CheckingListIncInvoiceValidationAndAccept (?, ?, ?, ?, ?)}");
    }

    @Override
    protected void SetQueryParams() throws SQLException {

        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setString(2,objectGuid);
        stmt.setInt(3, 1);
        stmt.registerOutParameter(4, Types.INTEGER);
        stmt.registerOutParameter(5, Types.INTEGER);
        stmt.registerOutParameter(6, Types.OTHER);


    }

    @Override
    public void Execute() throws SQLException {
        //super.Execute();
        SetQuery();
        createStatement();
        SetQueryParams();
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet != null) {
            setResultSet(resultSet);
        }
        acceptResult = new AcceptResult();
      //  stmt.getMoreResults();
        if (getResultSet()!= null) {
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

        setReturnCode(stmt.getInt(1));
        acceptResult.returnCode = getReturnCode();
        acceptResult.isValidated = stmt.getInt(4);
        acceptResult.isAccepted = stmt.getInt(5);
        acceptResult.eventMessage = stmt.getString(6);

    }

    public AcceptResult getAcceptResult() {
        if (acceptResult == null) {
            acceptResult = new AcceptResult();
        }
        return acceptResult;
    }
}
