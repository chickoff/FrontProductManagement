package ru.a5x5retail.frontproductmanagement.inventories.model;

import ru.a5x5retail.frontproductmanagement.BInfo;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListGoods;

public interface IInventoryUoWListener {

    void getBarcodeInfo(String barcode);
    void findInList(InputTatem tatem);
    void getQtyDialog(InputTatem tatem);
    void saveQtyValue(InputTatem tatem);
    void raiseError(String message);
    void raiseClear();
}
