package ru.a5x5retail.frontproductmanagement.checkinglistinc.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;
import ru.a5x5retail.frontproductmanagement.db.models.SKUContext;

public class BlaBlaUoW {


    private static final int SUCCESS = 0, FAIL = -1;

    public void setBarcodeInfoSelectedPosition(CheckingListPosition position) {
        this.barcodeInfo.setSelectedPosition(position);

        if (this.barcodeInfo.isLocal()) {
            changeNewQty(barcodeInfo,barcodeInfo.getLocalWeight());
            clear();
            return;
        }

        getNewQty(barcodeInfo);
    }

    // 3
    private BigDecimal newQty;
    public void setNewQty(BigDecimal qty) {
        this.newQty = qty;
        if (getMode() == 0 ) {
            changeNewQty(position,newQty);
        } else {
            changeNewQty(barcodeInfo,newQty);
        }
        clear();
    }

    private List<CheckingListPosition> checkingListPositionList;
    public void setCheckingListPositionList(List<CheckingListPosition> checkingListPositionList) {
        this.checkingListPositionList = checkingListPositionList;
    }

    // 2 - from barcode
    public void setSkuContext(SKUContext skuContext) {
        this.barcodeInfo.setSkuContext(skuContext);

        if (findPositionInLocalList() == -1 ) {
            return;
        }

        if ( barcodeInfo.getPositionListSize() == 0) {
            raiseError("Нет товара в накладной!");
            clear();
            return;
        }

        if (barcodeInfo.getPositionListSize() > 1) {
            selectOnePosition(barcodeInfo);
            return;
        }

        if (this.barcodeInfo.isLocal()) {
            changeNewQty(barcodeInfo,barcodeInfo.getLocalWeight());
            clear();
            return;
        }

        getNewQty(barcodeInfo);
    }

    // 1
    private CheckingListPosition position;
    public void setPosition(CheckingListPosition position) {
        if (barcodeInfo != null){
            raiseError("Действие не завершено!");
            clear();
            return;
        }
        this.position = position;
        getNewQty(this.position);
    }

    // 1
    private BarcodeInfo barcodeInfo;
    public void setBarcodeInfo(String barcode) {

        if (position != null){
            raiseError("Действие не завершено!");
            clear();
            return;
        }

        barcodeInfo = new BarcodeInfo(barcode);
        getSkuContext(barcodeInfo);
    }

    private IBlaBlaUoWEventsListener blaBlaUoWEventsListener;
    public void setBlaBlaUoWEventsListener(IBlaBlaUoWEventsListener blaBlaUoWEventsListener){
        this.blaBlaUoWEventsListener = blaBlaUoWEventsListener;
    }



    public void clear(){
        barcodeInfo = null;
        position = null;
        raiseClear();
    }

    private int findPositionInLocalList(){


        if (checkingListPositionList == null || checkingListPositionList.size() == 0){
            raiseError("Такого товара в накладной нет!");
            clear();
            return FAIL;
        }
        List<CheckingListPosition> pl = new ArrayList<>();
        for (CheckingListPosition checkingListPosition : checkingListPositionList) {
            if (checkingListPosition.code == barcodeInfo.getSkuContext().Code) {
                pl.add(checkingListPosition);
            }
        }
        barcodeInfo.setPositionList(pl);
        return SUCCESS;
    }

    private int getMode(){
        if (barcodeInfo == null && position != null) {
            return 0;
        } else if (barcodeInfo != null && position == null) {
            return 1;
        }
        else {
            return -1;
        }
    }

    public interface IBlaBlaUoWEventsListener {
        void getSkuContext(BarcodeInfo barcodeInfo);
        void getNewQty(CheckingListPosition position);
        void getNewQty(BarcodeInfo barcodeInfo);
        void selectOnePosition(BarcodeInfo barcodeInfo);
        void changeNewQty(CheckingListPosition position,BigDecimal newQty);
        void changeNewQty(BarcodeInfo barcodeInfo,BigDecimal newQty);
        void raiseError(String message);
        void raiseClear();
    }

    public void getSkuContext(BarcodeInfo barcodeInfo) {
        if (blaBlaUoWEventsListener == null) return;
        blaBlaUoWEventsListener.getSkuContext(barcodeInfo);
    }

    public void getNewQty(CheckingListPosition position) {
        if (blaBlaUoWEventsListener == null) return;
        blaBlaUoWEventsListener.getNewQty(position);
    }

    public void getNewQty(BarcodeInfo barcodeInfo) {
        if (blaBlaUoWEventsListener == null) return;
        blaBlaUoWEventsListener.getNewQty(barcodeInfo);
    }

    public void selectOnePosition(BarcodeInfo barcodeInfo) {
        if (blaBlaUoWEventsListener == null) return;
        blaBlaUoWEventsListener.selectOnePosition(barcodeInfo);
    }

    public void changeNewQty(CheckingListPosition position, BigDecimal newQty) {
        if (blaBlaUoWEventsListener == null) return;
        blaBlaUoWEventsListener.changeNewQty(position, newQty);
    }

    public void changeNewQty(BarcodeInfo barcodeInfo, BigDecimal newQty) {
        if (blaBlaUoWEventsListener == null) return;
        blaBlaUoWEventsListener.changeNewQty(barcodeInfo, newQty);
    }

    public void raiseError(String message) {
        if (blaBlaUoWEventsListener == null) return;
        blaBlaUoWEventsListener.raiseError(message);
    }

    public void raiseClear() {
        if (blaBlaUoWEventsListener == null) return;
        blaBlaUoWEventsListener.raiseClear();
    }
}
