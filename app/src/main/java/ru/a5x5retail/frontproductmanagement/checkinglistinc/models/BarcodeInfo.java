package ru.a5x5retail.frontproductmanagement.checkinglistinc.models;

import java.math.BigDecimal;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;
import ru.a5x5retail.frontproductmanagement.db.models.SKUContext;

public class BarcodeInfo {
    public String barcode;

    public BarcodeInfo(String barcode) {
        this.barcode = barcode;
    }

    public boolean isLocal() {

        if (barcode.length() < 13) {
            return false;
        }

        return Integer.parseInt(barcode.substring(0,1))  == 2;
    }

    public int getLocalSku() {
         if (!isLocal()) {
             return -1;
         }

        return Integer.parseInt(barcode.substring(2,7));
    }

    public BigDecimal getLocalWeight() {
        if (!isLocal()) {
            return null;
        }

        return BigDecimal.valueOf(Double.parseDouble(barcode.substring(7,9)+"."+ barcode.substring(9,12)));
    }

    private CheckingListPosition selectedPosition;
    public void setSelectedPosition(CheckingListPosition selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public CheckingListPosition getSelectedPosition() {
        return selectedPosition;
    }


    private List<CheckingListPosition> positionList;
    public List<CheckingListPosition> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<CheckingListPosition> position) {
        this.positionList = position;
    }

    public int getPositionListSize(){
        if (positionList == null) return 0;
        return positionList.size();
    }

    private SKUContext skuContext;
    public SKUContext getSkuContext() {
        return skuContext;
    }

    public void setSkuContext(SKUContext skuContext) {
        this.skuContext = skuContext;
    }
}
