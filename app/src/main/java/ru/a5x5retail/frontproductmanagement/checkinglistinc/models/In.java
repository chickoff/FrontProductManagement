package ru.a5x5retail.frontproductmanagement.checkinglistinc.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.BInfo;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;

public class In {

    private List<Integer> byClickMap;
    private List<Integer> byScanMap;

    public In() {
        inType = InputType.none;
        byClickMap = new ArrayList<>();
        byClickMap.add(10);byClickMap.add(11);

        byScanMap = new ArrayList<>();
        byScanMap.add(20);
        byScanMap.add(21);
        byScanMap.add(22);
        byScanMap.add(23);
        byScanMap.add(24);

        stepIndex = 0;
    }

    private InputType inType;
    public InputType getInType() {
        return inType;
    }
    public void setInType(InputType inType) {
        this.inType = inType;
    }

    private CheckingListPosition selectedPosition;
    public CheckingListPosition getSelectedPosition() {
        return selectedPosition;
    }
    public void setSelectedPosition(CheckingListPosition selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    private List<CheckingListPosition> positionListForChange;
    public List<CheckingListPosition> getPositionListForChange() {
        if (positionListForChange == null)
            positionListForChange = new ArrayList<>();
        return positionListForChange;
    }
    public void setPositionListForChange(List<CheckingListPosition> positionListForChange) {
        this.positionListForChange = positionListForChange;
    }

    private BInfo barcodeInfo;
    public BInfo getBarcodeInfo() {
        return barcodeInfo;
    }
    public void setBarcodeInfo(BInfo barcodeInfo) {
        this.barcodeInfo = barcodeInfo;
    }

    private BigDecimal newQty;
    public BigDecimal getNewQty() {
        return newQty;
    }
    public void setNewQty(BigDecimal newQty) {
        this.newQty = newQty;
    }

    public enum InputType {none,ByClick, ByScan}

    private int stepIndex;
    public void setNextStepIndex() {
        if (inType == InputType.ByClick){
            if (byClickMap.size() == stepIndex) return;
            stepIndex = ++stepIndex;
        }
        if (inType == InputType.ByScan){
            if (byScanMap.size() == stepIndex) return;
            stepIndex = ++stepIndex;
        }
    }

    public int getNextStep() {
        switch (inType){
            case ByClick:
                return byClickMap.get(stepIndex);
            case ByScan:
                return byScanMap.get(stepIndex);
            default:return -1;
        }
    }


}

