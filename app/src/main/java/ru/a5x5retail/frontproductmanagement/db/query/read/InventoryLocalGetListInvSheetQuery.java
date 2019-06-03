package ru.a5x5retail.frontproductmanagement.db.query.read;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.CheckListInentoryConverter;
import ru.a5x5retail.frontproductmanagement.db.converters.CheckingListHeadConverter;
import ru.a5x5retail.frontproductmanagement.db.converters.InventoryListConverter;
import ru.a5x5retail.frontproductmanagement.db.models.CheckListInventory;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.read.checkinglisthead.GetCheckListHeadParentQuery;

public class InventoryLocalGetListInvSheetQuery extends CallableQAsync {


    private String imei,inventoryGuid;

    public InventoryLocalGetListInvSheetQuery(String imei, String inventoryGuid) {

        this.inventoryGuid = inventoryGuid;
        this.imei = imei;
        headList = new ArrayList<>();
    }



    protected List<CheckListInventory> headList;
    public List<CheckListInventory> getList() {
        return headList;
    }

    @Override
    protected void SetQuery() {
        setSqlString("{? = call V_StoreTSD.dbo.InventoryLocalGetListInvSheet(?,?)}");
    }

    @Override
    protected void SetQueryParams() {
        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++, inventoryGuid);
            getStmt().setString(parameterIndex++, imei);
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
        CheckListInentoryConverter converter = new CheckListInentoryConverter();
        while (getResultSet().next()) {
            CheckListInventory head = new CheckListInventory();
            converter.Convert(getResultSet(), head);
            headList.add(head);
        }
    }
}
