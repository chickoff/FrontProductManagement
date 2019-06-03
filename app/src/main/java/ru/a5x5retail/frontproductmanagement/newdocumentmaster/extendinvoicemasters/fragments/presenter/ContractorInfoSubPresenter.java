package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments.presenter;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorExtendedInfo;
import ru.a5x5retail.frontproductmanagement.db.models.IncomeInvoiceHead;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.ExtendedContractorInfoModel;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments.view.IContractorInfoSubView;

@InjectViewState
public class ContractorInfoSubPresenter extends BasePresenter<IContractorInfoSubView> {

    public static final String TAG = "ExtendInvoiceMasterPresenter";

    @Override
    public String getGroupTag() {
        return TAG;
    }

    private ExtendedContractorInfoModel model;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
         model = ExtendedContractorInfoModel.getModel();
        getViewState().updateUi();
    }

    public List<IncomeInvoiceHead> getInvoiceHeadList() {
        return model.getInvoiceHeadList();
    }

    public ContractorExtendedInfo getContractorExtendedInfo() {
        return model.getContractorExtendedInfo();
    }


}
