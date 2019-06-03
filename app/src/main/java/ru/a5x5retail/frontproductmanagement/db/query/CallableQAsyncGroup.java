package ru.a5x5retail.frontproductmanagement.db.query;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.base.IDataBaseQueryEvent;

public class CallableQAsyncGroup extends DataBaseQuery

{

    private List<CallableQAsync> callableQAsyncList;

    public List<CallableQAsync> getCallableQAsyncList() {
        if (callableQAsyncList == null ) {
            callableQAsyncList = new ArrayList<>();
        }
        return callableQAsyncList;
    }

    public void addQuery(CallableQAsync query) {
        getCallableQAsyncList().add(query);
    }

    public  void removeQuery(CallableQAsync query) {
        getCallableQAsyncList().remove(query);
    }

    public void executeAsyncAll() {
        sendOnPreExecute();

        for (CallableQAsync callableQAsync : getCallableQAsyncList()) {
            callableQAsync.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
                @Override
                public void onPostExecute() {
                    check();
                }
            });
            callableQAsync.ExecuteAsync();
        }
    }


    private boolean isExecuted = false;
    private void check() {
        boolean b = true;
        for (CallableQAsync callableQAsync : getCallableQAsyncList()) {
            b = b & !callableQAsync.isRunning();
            if (b == false) break;
        }

        if (b && !isExecuted){
            isExecuted = true;
            sendOnPostExecute();
        }
    }

   /* private OnGroupPreExecuteListener onPreExecuteListener;
    private OnGroupPostExecuteListener onPostExecuteListener;
    public OnGroupPreExecuteListener getOnPreExecuteListener() {
        return onPreExecuteListener;
    }
    public void addOnPreExecuteListener(OnGroupPreExecuteListener onPreExecuteListener) {
        this.onPreExecuteListener = onPreExecuteListener;
    }
    public OnGroupPostExecuteListener getOnPostExecuteListener() {
        return onPostExecuteListener;
    }
    public void addOnPostExecuteListener(OnGroupPostExecuteListener onPostExecuteListener) {
        this.onPostExecuteListener = onPostExecuteListener;
    }
    public interface OnGroupPreExecuteListener {
        void onPreExecute();
    }
    public interface OnGroupPostExecuteListener {
        void onPostExecute();
    }*/
}
