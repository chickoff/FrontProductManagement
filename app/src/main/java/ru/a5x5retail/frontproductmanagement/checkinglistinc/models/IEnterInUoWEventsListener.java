package ru.a5x5retail.frontproductmanagement.checkinglistinc.models;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.BInfo;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;

public interface IEnterInUoWEventsListener {
    void getSkuContext(BInfo barcodeInfo);
    void findLocalPosition(BInfo barcodeInfo);
    void getNewQty(CheckingListPosition position);
    void selectOnePosition(List<CheckingListPosition> checkingListPositionList);
    void saveNewQty(In input);
    void raiseError(String message);

    void raiseClear();
}
