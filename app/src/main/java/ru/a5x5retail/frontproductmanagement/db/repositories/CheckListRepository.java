package ru.a5x5retail.frontproductmanagement.db.repositories;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetCheckingListIncControlQtyListQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetCheckingListIncMarkListQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetCheckingListIncPositionListQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.AddCheckigListPositionQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.EditCheckigListPositionDateQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.EditCheckigListPositionQtyQuery;
import ru.a5x5retail.frontproductmanagement.models.CheckList;
import ru.a5x5retail.frontproductmanagement.models.CheckListMarkList;
import ru.a5x5retail.frontproductmanagement.models.CheckListPositionList;

public class CheckListRepository {

    private CheckList checkList;
    public CheckList getCheckList() {
        if (checkList == null) {
            checkList = new CheckList();
        }
        return checkList;
    }

    public void setCheckList(CheckList checkList) {
        this.checkList = checkList;
    }

    public CheckListRepository(CheckingListHead checkingListHead) {
        getCheckList().setHead(checkingListHead);
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        MsSqlConnection con = new MsSqlConnection();
        return con.getConnection();
    }

    private void getDbPositions() throws SQLException, ClassNotFoundException {
        GetCheckingListIncPositionListQuery q1 = new GetCheckingListIncPositionListQuery(getConnection(),getCheckList().getHead().Guid);
        GetCheckingListIncControlQtyListQuery q2 = new GetCheckingListIncControlQtyListQuery(getConnection(),getCheckList().getHead().Guid);
        q1.Execute();
        q2.Execute();
        getCheckList().setCheckListPositionList(new CheckListPositionList(q1.getList(),q2.getList()));
    }

    private void getDbMarks() throws SQLException, ClassNotFoundException {
        GetCheckingListIncMarkListQuery q1 = new GetCheckingListIncMarkListQuery(getConnection(),getCheckList().getHead().Guid);
        q1.Execute();

        getCheckList().setCheckListMarkList(new CheckListMarkList(q1.getList()));
    }

    public void load() throws SQLException, ClassNotFoundException {
        getDbPositions();
        getDbMarks();
    }

    public void addQty(CheckingListPosition position, BigDecimal qty, int operationType) throws SQLException, ClassNotFoundException {
        EditCheckigListPositionQtyQuery qtyQuery =
                new EditCheckigListPositionQtyQuery(getConnection(),getCheckList().getHead().Guid, position.guid, qty,operationType);
        qtyQuery.Execute();
        if (operationType == 1) {
            position.qtyUser = position.qtyUser.add(qty).setScale(3);
        } else {
            position.qtyUser = qty;
        }

        if (!position.qtyUser.setScale(3).equals(qtyQuery.getNewQty().setScale(3)) ){
            getDbPositions();
        }

        //onDataChanged();
    }

    public void addDate(CheckingListPosition position, Date date) throws SQLException, ClassNotFoundException {
        EditCheckigListPositionDateQuery query =
                new EditCheckigListPositionDateQuery(getConnection(),getCheckList().getHead().Guid, position.guid,date);
        query.Execute();
        getDbPositions();

        //onDataChanged();
    }

    public void addNewSku(int sku) throws SQLException, ClassNotFoundException {

        AddCheckigListPositionQuery query = new AddCheckigListPositionQuery(getConnection(),getCheckList().getHead().Guid,sku);
        query.Execute();
        getDbPositions();

        //onDataChanged();
    }
}
