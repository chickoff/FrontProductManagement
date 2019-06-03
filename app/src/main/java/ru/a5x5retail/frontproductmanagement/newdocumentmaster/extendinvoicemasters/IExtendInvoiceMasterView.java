package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.a5x5retail.frontproductmanagement.interfaces.IBaseMvpView;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments.ExtendedContractorInfoFragment;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IExtendInvoiceMasterView extends IBaseMvpView {
    void showContractorFiterView();
    void showExtendedContractorInfoView();
}
