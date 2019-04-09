package ru.a5x5retail.frontproductmanagement.checkinglistinc;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.base.TypedViewModel;
import ru.a5x5retail.frontproductmanagement.db.converters.GetSKUContextConverter;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListManufacturerDate;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListMark;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;
import ru.a5x5retail.frontproductmanagement.db.models.SKUContext;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetCheckingListIncManufacturerDateListQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetCheckingListIncMarkListQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetCheckingListIncPositionListQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetSKUContextQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.AddCheckigListPositionQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.EditCheckigListPositionDateQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.EditCheckigListPositionQtyQuery;
import ru.a5x5retail.frontproductmanagement.db.repositories.CheckListRepository;
import ru.a5x5retail.frontproductmanagement.models.CheckList;

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

        if (selectedCheckingListHead == null) return;
        GetCheckingListIncPositionListQuery query = new GetCheckingListIncPositionListQuery(con.getConnection(),selectedCheckingListHead.Guid);
        query.Execute();
        checkingListPositionList = query.getList();
    }

    private void loadManufacturerDates() throws SQLException {
        if (selectedCheckingListHead == null) return;
        GetCheckingListIncManufacturerDateListQuery query  = new GetCheckingListIncManufacturerDateListQuery(con.getConnection(),selectedCheckingListHead.Guid);
        query.Execute();
        checkingListManufacturerDateList = query.getList();
    }

    private void loadMarks() throws SQLException {
        if (selectedCheckingListHead == null) return;
        GetCheckingListIncMarkListQuery query = new GetCheckingListIncMarkListQuery(con.getConnection(),selectedCheckingListHead.Guid);
        query.Execute();
        checkingListMarkList = query.getList();
    }


    public List<CheckingListPosition> getPositionListForDateOfManufacturer(){
        List<CheckingListPosition> tmp = new ArrayList<>();
        if (checkingListPositionList == null) return null;
        for (CheckingListPosition checkingListPosition : checkingListPositionList) {
            if (checkingListPosition.manufacturingDateFl == 1) {
                tmp.add(checkingListPosition);
            }
        }
        return tmp;
    }

    public List<CheckingListPosition> checkingListPositionList;
    public List<CheckingListManufacturerDate> checkingListManufacturerDateList;
    public List<CheckingListMark> checkingListMarkList;

    public CheckingListHead selectedCheckingListHead;

    public void addQty(CheckingListPosition position, BigDecimal qty, int operationType) throws SQLException {
        EditCheckigListPositionQtyQuery qtyQuery =
                new EditCheckigListPositionQtyQuery(con.getConnection(),position.checkingListIncHeadGuid, position.guid, qty,operationType);
        qtyQuery.Execute();
        if (operationType == 1) {
            position.qtyUser = position.qtyUser.add(qty).setScale(3);
        } else {
            position.qtyUser = qty;
        }

         if (!position.qtyUser.setScale(3).equals(qtyQuery.getNewQty().setScale(3)) ){
             loadPositionLists();
         }
        onDataChanged();
    }

    public void addDate(CheckingListPosition position, Date date) throws SQLException {
        EditCheckigListPositionDateQuery query =
                new EditCheckigListPositionDateQuery(con.getConnection(),position.checkingListIncHeadGuid, position.guid,date);
        query.Execute();
        loadPositionLists();
        onDataChanged();
    }

    public void addNewSku(int sku) throws SQLException {
        if (selectedCheckingListHead == null) {
            return;
        }
        AddCheckigListPositionQuery query = new AddCheckigListPositionQuery(con.getConnection(),selectedCheckingListHead.Guid,sku);
        query.Execute();
        loadPositionLists();
        onDataChanged();
    }

    public SKUContext getSkuContextByBarcode(String barcode) throws SQLException {
        GetSKUContextQuery query = new GetSKUContextQuery(con.getConnection(),barcode);
        query.Execute();

        if (query.getList() != null && query.getList().size() > 0) {
            return query.getList().get(0);
        } else {
            return null;
        }
    }



   public void Sort() {
       Collections.sort(checkingListPositionList);
   }


   /*********************************************************************************************/

   private CheckListRepository repository;

   //private CheckList

   public void l() throws SQLException, ClassNotFoundException {


       repository = new CheckListRepository(selectedCheckingListHead);

       repository.load();
       repository.getCheckList();
   }
}
