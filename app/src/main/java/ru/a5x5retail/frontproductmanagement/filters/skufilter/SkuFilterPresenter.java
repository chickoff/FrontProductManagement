package ru.a5x5retail.frontproductmanagement.filters.skufilter;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetFreeSkuForCheckigListQuery;

@InjectViewState
public class SkuFilterPresenter extends BasePresenter<ISkuFilterView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getFreeSkuForCheckigList();
    }


    /**********************************************************************************************/

    private List<CodeInfo> codeInfoList;
    public List<CodeInfo> getCodeInfoList() {
        return codeInfoList;
    }

    public void setCodeInfoList(List<CodeInfo> codeInfoList) {
        this.codeInfoList = codeInfoList;
        getViewState().updateUi();
    }

    /**********************************************************************************************/

    private String checkingListGuid;
    public String getCheckingListGuid() {
        return checkingListGuid;
    }

    public void setCheckingListGuid(String checkingListGuid) {
        this.checkingListGuid = checkingListGuid;
    }

    /**********************************************************************************************/

    public static final int ONE_SOURCE = 1;

    private int CaseOfSource = -1;

    public int getCaseOfSource() {
        return CaseOfSource;
    }

    public void setCaseOfSource(int caseOfSource) {
        CaseOfSource = caseOfSource;
    }


     GetFreeSkuForCheckigListQuery query;

    public void getFreeSkuForCheckigList() {
        query = new GetFreeSkuForCheckigListQuery(getCheckingListGuid());
        query.addAsyncQueryEventListener(new CallableQAsync.IAsyncQueryEventListener() {
            @Override
            public void onAsyncQueryEvent(CallableQAsync query, int returnCode, String returnMessage) {

            }
        });

        query.addAsyncExceptionEventListener(new CallableQAsync.IAsyncQueryExceptionListener() {
            @Override
            public void onAsyncQueryException(CallableQAsync query, Exception e) {

            }
        });

        query.addOnPreExecuteListener(new CallableQAsync.OnPreExecuteListener() {
            @Override
            public void onPreExecute() {
                getViewState().showAwaitDialog(true);
            }
        });

        query.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                getViewState().showAwaitDialog(false);
                setCodeInfoList(query.getList());
            }
        });

        query.ExecuteAsync();
    }

    public void cancelBackgroundProcess(){

    }

}




