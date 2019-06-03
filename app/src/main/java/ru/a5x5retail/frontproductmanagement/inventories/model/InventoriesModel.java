package ru.a5x5retail.frontproductmanagement.inventories.model;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.models.CheckListInventory;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListGoods;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db.models.DictionaryListGoods;
import ru.a5x5retail.frontproductmanagement.db.models.InventoryList;

public class InventoriesModel {

    private static InventoriesModel model;
    public static InventoriesModel getModel() {
        return model;
    }
    public static void createNewModel() {
        model = new InventoriesModel();
    }

    private List<InventoryList> inventoryLists;
    public List<InventoryList> getInventoryLists() {
        return inventoryLists;
    }
    public void setInventoryLists(List<InventoryList> inventoryLists) {
        this.inventoryLists = inventoryLists;
    }

    private InventoryList selectedInventoryList;

    public InventoryList getSelectedInventoryList() {
        return selectedInventoryList;
    }

    public void setSelectedInventoryList(InventoryList selectedInventoryList) {
        this.selectedInventoryList = selectedInventoryList;
    }


    /********************************************************************************************/

    private List<CheckListInventory> checkListInventories;

    public List<CheckListInventory> getCheckListInventories() {
        return checkListInventories;
    }

    public void setCheckListInventories(List<CheckListInventory> checkingListHeadList) {
        this.checkListInventories = checkingListHeadList;
    }

    /********************************************************************************************/
    private CheckListInventory selectedInventorySheet;
    public CheckListInventory getSelectedInventorySheet() {
        return selectedInventorySheet;
    }

    public void setSelectedInventorySheet(CheckListInventory selectedInventorySheet) {
        this.selectedInventorySheet = selectedInventorySheet;
    }


    /********************************************************************************************/

    private List<CheckingListGoods> goodsList;

    public List<CheckingListGoods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<CheckingListGoods> goodsList) {
        this.goodsList = goodsList;
    }

    /********************************************************************************************/

}
