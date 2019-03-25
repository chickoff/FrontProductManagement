package ru.a5x5retail.frontproductmanagement.checkinglistinc;

import java.sql.SQLException;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.base.TypedViewModel;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListManufacturerDate;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListMark;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetCheckingListIncManufacturerDateListQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetCheckingListIncMarkListQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetCheckingListIncPositionListQuery;

public class CheckingListIncViewModel extends TypedViewModel {

    MsSqlConnection con;

    public CheckingListIncViewModel() throws SQLException, ClassNotFoundException {
        con = new MsSqlConnection();
    }

    @Override
    public void Load() throws SQLException, ClassNotFoundException {
        super.Load();
        loadPositionLists();
        loadManufacturerDates();
        loadMarks();
    }

    private void loadPositionLists () throws SQLException {
        GetCheckingListIncPositionListQuery query = new GetCheckingListIncPositionListQuery(con.getConnection(),selectedCheckingListHead.Guid);
        query.Execute();
        checkingListPositionList = query.getList();
    }

    private void loadManufacturerDates() throws SQLException {
        GetCheckingListIncManufacturerDateListQuery query  = new GetCheckingListIncManufacturerDateListQuery(con.getConnection(),selectedCheckingListHead.Guid);
        query.Execute();
        checkingListManufacturerDateList = query.getList();
    }

    private void loadMarks() throws SQLException {
        GetCheckingListIncMarkListQuery query = new GetCheckingListIncMarkListQuery(con.getConnection(),selectedCheckingListHead.Guid);
        query.Execute();
        checkingListMarkList = query.getList();
    }


    public List<CheckingListPosition> checkingListPositionList;
    public List<CheckingListManufacturerDate> checkingListManufacturerDateList;
    public List<CheckingListMark> checkingListMarkList;

    public CheckingListHead selectedCheckingListHead;


}
