package ru.a5x5retail.frontproductmanagement.inventories.model;

import java.math.BigDecimal;

import ru.a5x5retail.frontproductmanagement.BInfo;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.models.In;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListGoods;
import ru.a5x5retail.frontproductmanagement.db_local.loSkuContext;

public class InventoryUoW {

    InputTatem inputTatem;
    private IInventoryUoWListener mListener;

    public InventoryUoW() {
        inputTatem = new InputTatem();
    }

    public void setListener(IInventoryUoWListener mListener) {
        this.mListener = mListener;
    }

    public void setSkuContext(loSkuContext skuContext) {
        if (!check()) {
            return;
        }
        inputTatem.setSkuContext(skuContext);
        inputTatem.setInputType(InputTatem.InputType.byFilter);
        analyze();
    }

    public void setBarcode(String barcode) {
        if (!check()) {
            return;
        }
        inputTatem.setBarcode(barcode);
        inputTatem.setInputType(InputTatem.InputType.byScan);
        analyze();
    }


    public void setGoods(CheckingListGoods goods, int oType) {
        setOperationType(oType);
        setGoods(goods);
    }

    public void setGoods(CheckingListGoods goods) {
        if (!check()) {
            return;
        }

        inputTatem.setSelectedPosition(goods);
        inputTatem.setInputType(InputTatem.InputType.byClick);
        analyze();
    }

    public void setLocalGoods(CheckingListGoods goods) {
        inputTatem.setSelectedPosition(goods);
        inputTatem.setNextStepIndex();
        analyze();
    }

    public void setBarcodeInfo(BInfo barcodeInfo) {
        if (barcodeInfo == null) {
            onError("Не найден товар по штрих-коду!");
            onClear();
            return;
        }

        inputTatem.setBarcodeInfo(barcodeInfo);
        inputTatem.setNextStepIndex();
        analyze();
    }

    public void setQtyValue(BigDecimal qtyValue) {
        inputTatem.setNewQty(qtyValue);
        inputTatem.setNextStepIndex();
        analyze();

    }

    protected void setOperationType(int oType) {
        inputTatem.setOperationType(oType);
    }

    private boolean check() {
        if (inputTatem.getInputType() != InputTatem.InputType.none) {
            onError("Действие не завершено");
            onClear();
            return false;
        }
        return true;
    }

    public void clear() {
        inputTatem = new InputTatem();
    }

    private void analyze() {
        switch (inputTatem.getNextStep()) {

            case 10 :
                getQtyDialog(inputTatem);
                break;
            case 11 :
                saveQtyValue(inputTatem);
                break;

/*****************************/

            case 20 :
                getBarcodeInfo(inputTatem.getBarcode());
                break;
            case 21 :
                findInList(inputTatem);
                break;
            case 22 :
                getQtyDialog(inputTatem);
                /*BInfo bi = inputTatem.getBarcodeInfo();
                if (bi.isLocal() && bi.isWeightAvailable()) {
                    setQtyValue(bi.getLocalWeight());
                    break;
                } else {

                }*/
                break;
            case 23 :
                saveQtyValue(inputTatem);
                break;

/*****************************/
            case 30 :
                findInList(inputTatem);
                break;
            case 31 :
                getQtyDialog(inputTatem);
                break;
            case 32 :
                saveQtyValue(inputTatem);
                break;
            default:
                break;
        }
    }

    private void getBarcodeInfo(String barcode) {
        if (mListener == null) return;
        mListener.getBarcodeInfo(barcode);
    }

    private void findInList(InputTatem tatem) {
        if (mListener == null) return;
        mListener.findInList(tatem);
    }

    private void getQtyDialog (InputTatem tatem){
        if (mListener == null) return;
        mListener.getQtyDialog(tatem);
    }

    private void  saveQtyValue(InputTatem tatem) {
        if (mListener == null) return;
        mListener.saveQtyValue(tatem);
    }

    private void onError(String message) {
        if (mListener == null) return;
        mListener.raiseError(message);
    }

    private void onClear() {
        if (mListener == null) return;
        mListener.raiseClear();
    }
}
