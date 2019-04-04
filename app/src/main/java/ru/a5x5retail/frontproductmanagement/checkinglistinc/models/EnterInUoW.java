package ru.a5x5retail.frontproductmanagement.checkinglistinc.models;

import java.math.BigDecimal;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.BInfo;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;
import ru.a5x5retail.frontproductmanagement.db.models.SKUContext;

public class EnterInUoW {


    private IEnterInUoWEventsListener mListener;

    public IEnterInUoWEventsListener getmListener() {
        return mListener;
    }

    public void setmListener(IEnterInUoWEventsListener mListener) {
        this.mListener = mListener;
    }

    private In input;
    private In getIn() {
        if (input == null) {
            input = new In();
        }
        return input;
    }

    public void setPosition(CheckingListPosition position) {
        check();
        getIn().setSelectedPosition(position);
        getIn().setInType(In.InputType.ByClick);
        analyze();
    }
    public void setBarcode(String barcode) {
        check();
        getIn().setBarcodeInfo(new BInfo(barcode));
        getIn().setInType(In.InputType.ByScan);
        analyze();
    }
    public void setSkuContext(SKUContext skuContext) {
        getIn().getBarcodeInfo().setSkuContext(skuContext);
        getIn().setNextStepIndex();
        analyze();
    }
    public void setPositionListForChange(List<CheckingListPosition> checkingListPositionList){
        getIn().setPositionListForChange(checkingListPositionList);
        getIn().setNextStepIndex();
        analyze();
    }
    public void setResultingPosition(CheckingListPosition position){
        getIn().setSelectedPosition(position);
        getIn().setNextStepIndex();
        analyze();
    }
    public void setNewQty(BigDecimal qty) {
        getIn().setNewQty(qty);
        getIn().setNextStepIndex();
        analyze();
    }

    private void analyze() {
       switch (getIn().getNextStep()) {

           case 10 :
               onGetQty(getIn().getSelectedPosition());
               break;
           case 11 :
               onSaveNewQty(getIn());
               break;
/*****************************/
           case 20 :
               onGetSkuContext(getIn().getBarcodeInfo());
               break;
           case 21 :
               onFindLocalPosition(getIn().getBarcodeInfo());
               break;
           case 22 :
               if (getIn().getPositionListForChange().size() == 0) {
                   onError("Нет товара в накладной!");
                   clear();
               }

               if (getIn().getPositionListForChange().size() == 1) {
                   setResultingPosition(getIn().getPositionListForChange().get(0));
               }

               if (getIn().getPositionListForChange().size() > 1) {
                   onSelectOnePosition(getIn().getPositionListForChange());
               }

               break;
           case 23 :

               BInfo bInfo = getIn().getBarcodeInfo();

               if (bInfo.isLocal()) {
                   if (bInfo.isWeightAvailable()) {
                       setNewQty(bInfo.getLocalWeight());
                       break;
                   }
               }
               onGetQty(getIn().getSelectedPosition());
               break;
           case 24 :
               onSaveNewQty(getIn());
               break;
           default:
               break;
       }
    }

    private void check() {
        if (getIn().getInType() != In.InputType.none) {
            onError("Действие не завершено");
            clear();
        }
    }

    public void clear() {
        input = null;
        onCleared();
    }

    private void onSaveNewQty(In input) {
        if (mListener != null) {
            mListener.saveNewQty(input);
        }
    }

    private void onSelectOnePosition(List<CheckingListPosition> checkingListPositionList) {
        if (mListener != null) {
            mListener.selectOnePosition(checkingListPositionList);
        }
    }

    private void onFindLocalPosition(BInfo barcodeInfo) {
        if (mListener != null) {
            mListener.findLocalPosition(barcodeInfo);
        }
    }

    private void onGetSkuContext(BInfo barcodeInfo) {
        if (mListener != null) {
            mListener.getSkuContext(barcodeInfo);
        }
    }

    private void onGetQty(CheckingListPosition position) {
        if (mListener != null) {
            mListener.getNewQty(position);
        }
    }

    private void onError(String message) {
        if (mListener != null) {
            mListener.raiseError(message);
        }
    }

    private void onCleared() {
        if (mListener != null) {
            mListener.raiseClear();
        }
    }
}

