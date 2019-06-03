package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments.presenter;

import com.arellomobile.mvp.InjectViewState;

import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.bus;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorInfo;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments.view.IExtendedContractorInfoView;

@InjectViewState
public class ExtendedContractorInfoPresenter extends BasePresenter<IExtendedContractorInfoView> {


    public static final String TAG = "ExtendInvoiceMasterPresenter";

    public ExtendedContractorInfoPresenter() {
    }



    @Override
    public String getGroupTag() {
        return TAG;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        showIncomeInfoSub();
    }

    public void showIncomeInfoSub() {
       getViewState().showIncomeInfoSub();
    }

    public void showPlanIncomeInfoSub() {
        getViewState().showPlanIncomeInfoSub();
    }

    public void showContractorInfoSub() {
        getViewState().showContractorInfoSub();
    }

    @Override
    public void receiveBusMessage(bus.BusMessage msg) {
        bus.BusMessage d = msg;
        d =d;
        contractorInfo = (ContractorInfo) msg.data;
    }

    //    @Override
//    public void receiveBusMessage(bus.BusMessage msg) {
//        if (msg.To.equals(this.getClass().toString())) {
//            contractorInfo = (ContractorInfo) msg.data;
//        }
//    }

    ContractorInfo contractorInfo;
}
