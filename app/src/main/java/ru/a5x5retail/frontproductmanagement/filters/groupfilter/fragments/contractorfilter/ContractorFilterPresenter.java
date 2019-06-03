package ru.a5x5retail.frontproductmanagement.filters.groupfilter.fragments.contractorfilter;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetAllContractorsQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetSkuByContractorGuidQuery;


@InjectViewState
public class ContractorFilterPresenter extends BasePresenter<IContractorFilterView> {
    public static final String TAG = "ContractorFilterPresenter";

/*

new CallableQAsync.OnPostExecuteListener() {
        @Override
        public void onPostExecute() {
            contractorList = q.getList();
        }
    }
*/

    public ContractorFilterPresenter() {
    }

    @Override
    public void attachView(IContractorFilterView view) {
        super.attachView(view);
        //getContractor();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getContractor();
    }

    public void getContractor() {

        final GetAllContractorsQuery q = new GetAllContractorsQuery();
        q.addOnPreExecuteListener(new CallableQAsync.OnPreExecuteListener() {
            @Override
            public void onPreExecute() {
                getViewState().showAwaitDialog(true);
            }
        });
        q.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                getContractorList(q);
                getViewState().showAwaitDialog(false);
            }
        });
       q.ExecuteAsync();
    }

    private void getContractorList(GetAllContractorsQuery q){
        getViewState().setContractorList(q.getList());
    }

    public void getCode(String contractorGuid) {

        final GetSkuByContractorGuidQuery q = new GetSkuByContractorGuidQuery(contractorGuid);
        q.addOnPreExecuteListener(new CallableQAsync.OnPreExecuteListener() {
            @Override
            public void onPreExecute() {
                getViewState().showAwaitDialog(true);
            }
        });
        q.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                getCodeList(q);
                getViewState().showAwaitDialog(false);
            }
        });
        q.ExecuteAsync();


    }


    protected void getCodeList(GetSkuByContractorGuidQuery q) {
        getViewState().setCodeList(q.getList());
    }
}
