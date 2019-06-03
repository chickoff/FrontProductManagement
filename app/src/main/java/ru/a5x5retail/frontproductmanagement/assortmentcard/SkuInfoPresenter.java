package ru.a5x5retail.frontproductmanagement.assortmentcard;

import com.arellomobile.mvp.InjectViewState;

import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.bus;
import ru.a5x5retail.frontproductmanagement.db.models.AssortmentSku;

@InjectViewState
public class SkuInfoPresenter extends BasePresenter<ISkuInfoView> {


    AssortmentSku sku;
    @Override
    public void receiveBusMessage(bus.BusMessage msg) {
        if (msg.To != this.getClass().getName()) return;
        if (msg.data instanceof AssortmentSku) {
            sku = (AssortmentSku) msg.data;
            getViewState().updateUi();
        } else {
            getViewState().clearUi();
        }
    }
}
