package ru.a5x5retail.frontproductmanagement.db.query.read.checkinglisthead;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.CheckingListHeadConverter;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQuery;

public class GetCheckingHeadListQuery extends GetCheckListHeadParentQuery {

    private int typeDocId;
    private String imei;
    public GetCheckingHeadListQuery(String imei, Integer typeDocId) {

        this.typeDocId = typeDocId;
        this.imei = imei;
        headList = new ArrayList<>();
    }

    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.CheckingListGetHead(?,?)}");
    }

    @Override
    protected void SetQueryParams() {
        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++, imei);
            getStmt().setInt(parameterIndex++, typeDocId);
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
    }

/*    @Override
    protected void Execute() {
            super.Execute();
            try {
                boolean b = getStmt().execute();
                setResultSet(getStmt().getResultSet());
                CheckingListHeadConverter converter = new CheckingListHeadConverter();
                while (getResultSet().next()) {
                    CheckingListHead head = new CheckingListHead();
                    converter.Convert(getResultSet(), head);
                    headList.add(head);
                }
                getStmt().getMoreResults();
                setReturnCode((int) getStmt().getObject(1));
                int r = getReturnCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }*/

    @Override
    protected void parseResultSet() throws SQLException {
        CheckingListHeadConverter converter = new CheckingListHeadConverter();
        while (getResultSet().next()) {
            CheckingListHead head = new CheckingListHead();
            converter.Convert(getResultSet(), head);
            headList.add(head);
        }
    }
}
