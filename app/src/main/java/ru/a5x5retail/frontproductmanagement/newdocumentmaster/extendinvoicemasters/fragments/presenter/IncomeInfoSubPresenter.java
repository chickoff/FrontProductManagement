package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments.presenter;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorExtendedInfo;
import ru.a5x5retail.frontproductmanagement.db.models.IncomeInvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.query.create.CreateCheckingListIncDocQuery;
import ru.a5x5retail.frontproductmanagement.db_local.ProjectMap;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.ExtendedContractorInfoModel;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments.view.IIncomeInfoSubView;

@InjectViewState
public class IncomeInfoSubPresenter extends BasePresenter<IIncomeInfoSubView> {
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
     //   getViewState().updateUi();
    }

    @Override
    public void attachView(IIncomeInfoSubView view) {
        super.attachView(view);
        getViewState().updateUi();
    }

    public List<IncomeInvoiceHead> getInvoiceHeadList() {
        return model.getInvoiceHeadList();
    }

    public ContractorExtendedInfo getContractorExtendedInfo() {
        return model.getContractorExtendedInfo();
    }

    public void createNewCheckList(IncomeInvoiceHead head) {
        CreateCheckingListIncDocQuery query = new CreateCheckingListIncDocQuery(
                head.guid, AppConfigurator.getDeviceId(ProdManApp.getAppContext()),
                ProjectMap.getCurrentTypeDoc().getTypeOfDocument().getIndex(),head.sourceTypeIdd
        );
        registerAwaitEvents(query);
        query.ExecuteAsync();
    }
}
