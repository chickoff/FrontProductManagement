package ru.a5x5retail.frontproductmanagement.assortmentcard;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.bus;
import ru.a5x5retail.frontproductmanagement.db.models.AssortmentSku;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.DataBaseQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetAssortmentSKUQuery;


@InjectViewState
public class AssortmentCardActivityPresenter extends BasePresenter<IAssortmentCardView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
       getViewState().viewSkuInfo();
    }

    public void setBarcode(String barcode) {
        final GetAssortmentSKUQuery query = new GetAssortmentSKUQuery(barcode);
        registerAwaitEvents(query);


        query.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                setC(query.getList());
            }
        });

        query.addAsyncQueryEventListener(new DataBaseQuery.IAsyncQueryEventListener() {
            @Override
            public void onAsyncQueryEvent(CallableQAsync query, int returnCode, String returnMessage) {
                if (returnCode != 0) {
                    getViewState().showEventToast(returnMessage, 0);
                }
            }
        });

        query.ExecuteAsync();
    }


    private void setC(List<AssortmentSku> skuList) {
        AssortmentSku sku = null;
        if (!(skuList == null || skuList.size() == 0)) {
            sku = skuList.get(0);
        }
        sendBusMessage(this.getClass().getName(),SkuInfoPresenter.class.getName(),sku);
    }
}
