package ru.a5x5retail.frontproductmanagement.inventories.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.BInfo;
import ru.a5x5retail.frontproductmanagement.db.models.CheckListInventory;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListGoods;
import ru.a5x5retail.frontproductmanagement.db.models.InventoryCode;
import ru.a5x5retail.frontproductmanagement.db_local.loSkuContext;


public class InputTatem {

    private BInfo barcodeInfo;
    public BInfo getBarcodeInfo() {
        return barcodeInfo;
    }
    public void setBarcodeInfo(BInfo barcodeInfo) {
        this.barcodeInfo = barcodeInfo;
    }


    private InputType inputType;
    public InputType getInputType() {
        return inputType;
    }
    public void setInputType(InputType inputType) {
        this.inputType = inputType;
    }


    private CheckingListGoods selectedPosition;
    public CheckingListGoods getSelectedPosition() {
        return selectedPosition;
    }
    public void setSelectedPosition(CheckingListGoods selectedPosition) {
        this.selectedPosition = selectedPosition;
    }


    private loSkuContext skuContext;
    public loSkuContext getSkuContext() {
        return skuContext;
    }
    public void setSkuContext(loSkuContext skuContext) {
        this.skuContext = skuContext;
    }




    private InventoryCode inventoryCode;
    public InventoryCode getInventoryCode() {
        return inventoryCode;
    }
    public void setInventoryCode(InventoryCode inventoryCode) {
        this.inventoryCode = inventoryCode;
    }

    /******************************************************************************************/


    public int getSku() {
        int var = -1;
        switch (inputType) {
            case none:
                break;
            case byClick:
                var = selectedPosition.Code;
                break;
            case byScan:
                var = barcodeInfo.getSkuContext().Code;
                break;
            case byFilter:
                var = (int) skuContext.Code;
                break;
            case byEdit:
                var = inventoryCode.code;
                break;
        }
        return var;
    }

    public int getMeasureUnitIdd() {
        int var = -1;
        switch (inputType) {
            case none:
                break;
            case byClick:
                var = selectedPosition.MeasureUnitIDD;
                break;
            case byScan:
                var = barcodeInfo.getSkuContext().MeasureUnitIDD;
                break;
            case byFilter:
                var = skuContext.MeasureUnitIDD;
                break;
            case byEdit:
                var = inventoryCode.measureUnitIDD;
                break;
        }
        return var;
    }

    public String getNameLong() {
        String var = "";
        switch (inputType) {
            case none:
                break;
            case byClick:
                var = selectedPosition.NameLong;
                break;
            case byScan:
                var =barcodeInfo.getSkuContext().NameLong;
                break;
            case byFilter:
                var = skuContext.NameLong;
                break;
            case byEdit:
                var = inventoryCode.nameLong;
                break;
        }
        return var;
    }

    private String barcode;
    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public BigDecimal getCurrentQty() {
        BigDecimal var = BigDecimal.valueOf(0);

        if (inputType == InputType.byEdit) {
            if (inventoryCode != null && inventoryCode.qty != null) {
                var = inventoryCode.qty;
            }
        } else {
            if (selectedPosition != null) {
                var = selectedPosition.Qty;
            }
        }

        return var;
    }

    private BigDecimal newQty;
    public BigDecimal getNewQty() {
        if (inputType == InputType.byScan && barcodeInfo != null) {
            if (barcodeInfo.isLocal() && barcodeInfo.isWeightAvailable()) {
                newQty = barcodeInfo.getLocalWeight();
            }
        }

        if (newQty == null) {
            newQty = BigDecimal.valueOf(0);
        }
        return newQty;
    }
    public void setNewQty(BigDecimal newQty) {
        this.newQty = newQty;
    }


    private int operationType = 1;
    public int getOperationType() {
        return operationType;
    }
    public void setOperationType(int oType) {
        this.operationType = oType;
    }




    /******************************************************************************************/

    private List<Integer> byClickMap;
    private List<Integer> byScanMap;
    private List<Integer> byFilterMap;




    public InputTatem() {

        byClickMap = new ArrayList<>();
        byClickMap.add(10);
        byClickMap.add(11);

        byScanMap = new ArrayList<>();
        byScanMap.add(20);
        byScanMap.add(21);
        byScanMap.add(22);
        byScanMap.add(23);

        byFilterMap = new ArrayList<>();
        byFilterMap.add(30);
        byFilterMap.add(31);
        byFilterMap.add(32);
        byFilterMap.add(33);

        inputType = InputType.none;
        stepIndex = 0;
    }



    public enum InputType {none, byClick, byScan, byFilter, byEdit}

    private int stepIndex;
    public void setNextStepIndex() {
        if (inputType == InputType.byClick){
            if (byClickMap.size() == stepIndex) return;
            stepIndex = ++stepIndex;
        }
        if (inputType == InputType.byScan){
            if (byScanMap.size() == stepIndex) return;
            stepIndex = ++stepIndex;
        }

        if (inputType == InputType.byFilter){
            if (byScanMap.size() == stepIndex) return;
            stepIndex = ++stepIndex;
        }
    }

    public int getNextStep() {
        switch (inputType){
            case byClick:
                return byClickMap.get(stepIndex);
            case byScan:
                return byScanMap.get(stepIndex);
            case byFilter:
                return byFilterMap.get(stepIndex);
            default:return -1;
        }
    }
}
