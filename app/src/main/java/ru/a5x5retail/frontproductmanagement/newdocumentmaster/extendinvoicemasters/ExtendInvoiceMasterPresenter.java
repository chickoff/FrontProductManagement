package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters;

import com.arellomobile.mvp.InjectViewState;

import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.bus;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsyncGroup;
import ru.a5x5retail.frontproductmanagement.db.query.DataBaseQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetCheckingListIncIncomesQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetPlanIncomeListQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.async.GetExtendedContractorInfoQueryAsync;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments.presenter.ExtendedContractorInfoPresenter;

@InjectViewState
public class ExtendInvoiceMasterPresenter extends BasePresenter<IExtendInvoiceMasterView> {
    public static final String TAG = "ExtendInvoiceMasterPresenter";
    @Override
    public String getGroupTag() {
        return TAG;
    }
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showContractorFiterView();
    }

    public void setSelectedContractor(ContractorInfo contractor) {
        ExtendedContractorInfoModel.clearModel();
        ExtendedContractorInfoModel model = ExtendedContractorInfoModel.getModel();
        model.setExternalContractorGuid(contractor.guid);
        load();
    }

    private void load() {
        final ExtendedContractorInfoModel model = ExtendedContractorInfoModel.getModel();
        final GetExtendedContractorInfoQueryAsync q1 = new GetExtendedContractorInfoQueryAsync(model.getExternalContractorGuid());
        q1.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                model.setContractorExtendedInfo(q1.getContractorExtendedInfo());
            }
        });
        final GetPlanIncomeListQuery q2 = new GetPlanIncomeListQuery(model.getExternalContractorGuid());
        q2.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                model.setPlanIncomeList(q2.getPlanIncomeList());
            }
        });
        final GetCheckingListIncIncomesQuery q3 = new GetCheckingListIncIncomesQuery(model.getExternalContractorGuid());
        q3.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                model.setInvoiceHeadList(q3.getList());
            }
        });

        CallableQAsyncGroup group = new CallableQAsyncGroup();
        group.addQuery(q1);
        group.addQuery(q2);
        group.addQuery(q3);

        registerAwaitEvents(group);

        group.addOnPostExecuteListener(new DataBaseQuery.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                getViewState().showExtendedContractorInfoView();
            }
        });

        group.executeAsyncAll();

    }


    public void loadIncomeList() {
        final ExtendedContractorInfoModel model = ExtendedContractorInfoModel.getModel();
        final GetCheckingListIncIncomesQuery q3 = new GetCheckingListIncIncomesQuery(model.getExternalContractorGuid());
        q3.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                model.setInvoiceHeadList(q3.getList());
            }
        });
        registerAwaitEvents(q3);
        q3.ExecuteAsync();
    }

    /*private void LoadContractorInfo() {
        final GetExtendedContractorInfoQueryAsync q = new GetExtendedContractorInfoQueryAsync(externalContractorGuid);
        q.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                setContractorExtendedInfo(q.getContractorExtendedInfo());
                onDataChanged();
            }
        });




        q.ExecuteAsync();
       *//* if (!q.isResultSetEnable() && q.getException() != null) {
            throw q.getException();
        }*//*
    }

    private void LoadPlanIncome() {
        final GetPlanIncomeListQuery q = new GetPlanIncomeListQuery(externalContractorGuid);
        q.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                planIncomeList = q.getPlanIncomeList();
            }
        });
        q.ExecuteAsync();

    }

    private void LoadIncome() {

        final GetCheckingListIncIncomesQuery q = new GetCheckingListIncIncomesQuery(externalContractorGuid);
        q.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                invoiceHeadList = q.getList();
            }
        });
        q.ExecuteAsync();

    }
    */
}
