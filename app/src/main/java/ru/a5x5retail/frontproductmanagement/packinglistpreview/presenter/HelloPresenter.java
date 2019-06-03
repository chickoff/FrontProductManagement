package ru.a5x5retail.frontproductmanagement.packinglistpreview.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.db.models.AcceptResult;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.DataBaseQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.UpdateDecommissionSpoilInRrQuery;
import ru.a5x5retail.frontproductmanagement.db.query.update.async.UpdateCheckingListIncSourceQueryAsync;
import ru.a5x5retail.frontproductmanagement.db.query.update.async.UpdateLocalInventoryInRrQueryAsync;
import ru.a5x5retail.frontproductmanagement.db.query.update.async.ValidationAndAcceptInvoiceQueryAsync;
import ru.a5x5retail.frontproductmanagement.db_local.ProjectMap;
import ru.a5x5retail.frontproductmanagement.packinglistpreview.view.IPackingListPreView;


@InjectViewState
public class HelloPresenter extends BasePresenter<IPackingListPreView> {
    public static final String TAG = "RepositoryLikesPresenter";
    public HelloPresenter() {

    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        showPreviewFragment();

        head = ProjectMap.getCurrentCheckingListHead();

        /*for (int i =0; i < 100; i++){

            try {
                Log.e("qqqqqqqqqqqqqqqqqqqq",head.Guid) ;
                CheckingListHead ccc = ProjectMap.getCurrentCheckingListHead();
                Log.e("sss",ccc.Guid) ;
                UUID bb = UUID.fromString(head.Guid);
            } catch (Exception e) {
                e.printStackTrace();
                head = ProjectMap.getCurrentCheckingListHead();
            }

        }*/


    }

    public void showPreviewFragment() {
        getViewState().showPreviewFragment();
    }
    public void showAcceptingFragment() {
        getViewState().showAcceptingFragment();
    }

    public void showAwaitDialog(boolean show) {
        getViewState().showAwaitDialog(show);
    }

    public void doSyncInRr() {
        CallableQAsync q = null;

        switch (ProjectMap.getCurrentTypeDoc().getTypeOfDocument()){
            case PARTIAL_INVENTORY:
                q = new UpdateLocalInventoryInRrQueryAsync(head.Guid);
                break;
            case FULL_INVENTORY:
                break;
            case OUTER_INCOME:
                q = new UpdateCheckingListIncSourceQueryAsync(head.Guid);
                break;
            case INNER_INCOME:
                q = new UpdateCheckingListIncSourceQueryAsync(head.Guid);
                break;
            case DISCARD:
                q = new UpdateDecommissionSpoilInRrQuery(head.Guid);
                break;
            case SETTINGS:
                break;
        }

        registerAwaitEvents(q);


        q.addAsyncExceptionEventListener(new DataBaseQuery.IAsyncQueryExceptionListener() {
            @Override
            public void onAsyncQueryException(CallableQAsync query, Exception e) {
                getViewState().showExceptionToast(e,e.getMessage());
            }
        });

        q.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                getViewState().showEventToast("Завершено",0);
            }
        });

        q.ExecuteAsync();

    }


    private AcceptResult lastAcceptResult;
    public AcceptResult getLastAcceptResult() {
        return lastAcceptResult;
    }

    public void setLastAcceptResult(AcceptResult lastAcceptResult) {
        this.lastAcceptResult = lastAcceptResult;
    }

    public void doCheckListAccept() {
        final ValidationAndAcceptInvoiceQueryAsync q1 = new ValidationAndAcceptInvoiceQueryAsync(head.Guid);
        registerAwaitEvents(q1);
        q1.addAsyncQueryEventListener(new CallableQAsync.IAsyncQueryEventListener() {
            @Override
            public void onAsyncQueryEvent(CallableQAsync query, int returnCode, String returnMessage) {
                lastAcceptResult = q1.getAcceptResult();
                getViewState().showEventToast(returnMessage,0);
                getViewState().updateUi();

            }
        });

        q1.addAsyncExceptionEventListener(new CallableQAsync.IAsyncQueryExceptionListener() {
            @Override
            public void onAsyncQueryException(CallableQAsync query, Exception e) {
                getViewState().showExceptionToast(e,e.getMessage());
            }
        });

        q1.ExecuteAsync();
    }


    private CheckingListHead head;
    public CheckingListHead getHead() {
        return head;
    }

    public void setHead(CheckingListHead head) {
        this.head = head;
    }


}
