package ru.a5x5retail.frontproductmanagement.newdocumentmaster.inventorymaster;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import ru.a5x5retail.frontproductmanagement.base.BaseViewModel;
import ru.a5x5retail.frontproductmanagement.db.models.InventoryList;

import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetLocalInventoryListQuery;

import java.sql.SQLException;
import java.util.List;

public class InventoryMasterViewModel extends BaseViewModel {

    private List<InventoryList> inventoryList;
    private MutableLiveData<InventoryList> selectedInventoryList;

    public InventoryMasterViewModel() {
        selectedInventoryList = new MutableLiveData<>();
    }

    @Override
    public void Load() throws SQLException, ClassNotFoundException {
        super.Load();
        MsSqlConnection con = new MsSqlConnection();
        GetLocalInventoryListQuery query =
                new GetLocalInventoryListQuery(con.getConnection());
        try {
            con.CallQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        inventoryList = query.getInventoryList();
    }

    public List<InventoryList> getInventoryList() {
        return inventoryList;
    }


    public LiveData<InventoryList> getSelectedInventoryList() {
        return selectedInventoryList;
    }

    public void setSelectedInventoryList(InventoryList selectedInventoryList) {
        this.selectedInventoryList.setValue(selectedInventoryList);
    }
}
