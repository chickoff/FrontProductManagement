package ru.a5x5retail.frontproductmanagement.base;

import com.arellomobile.mvp.MvpPresenter;


import ru.a5x5retail.frontproductmanagement.bus;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.DataBaseQuery;
import ru.a5x5retail.frontproductmanagement.interfaces.IBaseMvpView;

public class BasePresenter<T extends IBaseMvpView> extends MvpPresenter<T>

implements bus.IPresenterReceiveMessage {

    public BasePresenter() {
        addToBus();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        delFormBus();
    }

    protected void addToBus(){
        bus.addListener(this);
    }

    protected void delFormBus() {
        bus.removeListener(this);
    }


    public void showAwaitDialog(boolean show) {
        getViewState().showAwaitDialog(show);
    }


    public void receiveBusMessage(bus.BusMessage msg){

    }

    public String getGroupTag(){
        return "Default";
    }

    public void sendBusMessage(String from,String to, Object data) {
        bus.BusMessage msg = new bus.BusMessage();
        msg.from = from;
        msg.To = to;
        msg.data = data;
        bus.sendBusMessage(this,msg);
    }

    /*****************************************************************************************************/


    public void registerAwaitEvents(IDataBaseQueryEvent query) {
        query.addOnPreExecuteListener(new CallableQAsync.OnPreExecuteListener() {
            @Override
            public void onPreExecute() {
                showAwaitDialog(true);
            }
        });

        query.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                showAwaitDialog(false);
            }
        });

        query.addOnProgressUpdateListener(new CallableQAsync.OnProgressUpdateListener() {
            @Override
            public void onProgressUpdate(String... values) {
                getViewState().showEventToast(values[0],0);
            }
        });
    }

    public void  registerMsgEvents(IDataBaseQueryEvent query){
        query.addAsyncExceptionEventListener(new DataBaseQuery.IAsyncQueryExceptionListener() {
            @Override
            public void onAsyncQueryException(CallableQAsync query, Exception e) {
                getViewState().showExceptionToast(e,e.getMessage());
            }
        });

        query.addAsyncQueryEventListener(new DataBaseQuery.IAsyncQueryEventListener() {
            @Override
            public void onAsyncQueryEvent(CallableQAsync query, int returnCode, String returnMessage) {
                if (returnMessage != null)
                    getViewState().showEventToast(returnMessage,0);
            }
        });

    }


   /* public void registerViewEvents(IDataBaseQueryEvent query) {

        query.addAsyncExceptionEventListener(new CallableQAsync.IAsyncQueryExceptionListener() {
            @Override
            public void onAsyncQueryException(CallableQAsync query, Exception e) {
                showAwaitDialog(false);
            }
        });

        query.addAsyncQueryEventListener(new CallableQAsync.IAsyncQueryEventListener() {
            @Override
            public void onAsyncQueryEvent(CallableQAsync query, int returnCode, String returnMessage) {
                showAwaitDialog(false);
            }
        });

        query.addOnPreExecuteListener(new CallableQAsync.OnPreExecuteListener() {
            @Override
            public void onPreExecute() {
                showAwaitDialog(true);
            }
        });

        query.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                showAwaitDialog(false);
            }
        });


    }*/


    /*****************************************************************************************************/

}
