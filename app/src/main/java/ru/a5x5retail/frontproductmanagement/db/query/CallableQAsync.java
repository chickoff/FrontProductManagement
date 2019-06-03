package ru.a5x5retail.frontproductmanagement.db.query;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.base.IDataBaseQueryEvent;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;

public abstract class CallableQAsync extends DataBaseQuery

{

    public CallableQAsync()  {
        super();
        Log.d("DataBaseQuery",this.toString());
    }

    /**********************************************************************************************/
    /*************************  Из BaseAsyncQuery  ************************************************/
    /**********************************************************************************************/

    private String sqlString;
    private Exception exception;
    public int returnCode;



    public String eventMessage;
    private ResultSet rs;
    private boolean isDoError;
    private boolean isRunning;

    protected  int parameterIndex = 1;



    protected Connection getConnection() {
        Connection c = null;
        long l = 1;
        while (c ==null) {
            if (l % 6 == 0) {
                task.publishIt("Не удается подключиться!");
            }
            try {
                c = new MsSqlConnection().getConnection();
            } catch (Exception e) {
                e.printStackTrace();

                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    e.printStackTrace();
                }

                l++;
            }
        }
        return c;    }

    public String getSqlString() {
        return sqlString;
    }

    public void setSqlString(String sqlString) {
        this.sqlString = sqlString;
    }

    public ResultSet getResultSet() {
        return rs;
    }

    public void setResultSet(ResultSet rs) {
        this.rs = rs;
    }

    public String getEventMessage() {
        return eventMessage;
    }

    public void setEventMessage(String eventMessage) {
        this.eventMessage = eventMessage;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    protected void setReturnCode() throws SQLException {
       /* if (stmt == null) {
            return;
        }
        ResultSet rs = stmt.getResultSet();

       if (rs.isBeforeFirst()) {
            boolean b = stmt.getMoreResults(0);
            b = b;
        }
        setReturnCode((int) stmt.getObject(1));*/

    }

    public Exception getException() {
        return exception;
    }

    protected void setException(Exception exception) {
        this.exception = exception;
        ProdManApp.exceptionToFile(exception);
        isDoError = true;
    }

    public boolean isDoError() {
        return isDoError;
    }

    public boolean isRunning() {
        return isRunning;
    }

    protected void setRunning(boolean running) {
        isRunning = running;
    }

    protected void sendException(CallableQAsync query, Exception e) {
        if (asyncQueryExceptionListenerList != null) {
            for (CallableQAsync.IAsyncQueryExceptionListener iAsyncQueryEventListener : asyncQueryExceptionListenerList) {
                iAsyncQueryEventListener.onAsyncQueryException(query,e);
            }
        }
    }

    /**********************************************************************************************/


    protected void sendEvent(CallableQAsync query, int returnCode, String returnMessage) {
        if (asyncQueryEventListenerList != null) {
            for (CallableQAsync.IAsyncQueryEventListener iAsyncQueryEventListener : asyncQueryEventListenerList) {
                iAsyncQueryEventListener.onAsyncQueryEvent(query,returnCode,returnMessage);
            }
        }
    }


    /**********************************************************************************************/
    /**********************************************************************************************/


    private boolean isRowsAvailable;

    public boolean isRowsAvailable() {
        return isRowsAvailable;
    }

    protected void setRowsAvailable(boolean rowsAvailable) {
        isRowsAvailable = rowsAvailable;
    }

    /**********************************************************************************************/


    protected CallableStatement stmt = null;




    DbTask task = null;

    public void ExecuteAsync() {
        task = new DbTask();
        task.execute();
    }

    protected CallableStatement getStmt() {
       // createStatement();
        return stmt;
    }

    protected void createStatement() throws SQLException {
        if (stmt == null) {

            stmt = getConnection().prepareCall(getSqlString());

        }
    }

    protected abstract void SetQuery();
    protected abstract void SetQueryParams() throws SQLException;

    protected boolean Execute(){

        /*try {
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    /*    if (isDoError()) {return;}

        if (isDoError()) {return;}

        if (isDoError()) {return;}*/


        try {
            SetQuery();
            createStatement();
            SetQueryParams();
            stmt.execute();
            while (stmt.getUpdateCount() != -1 && !stmt.getMoreResults()) {
                stmt.getMoreResults();
            }

            ResultSet sssss = stmt.getResultSet();
            sssss  = sssss;

            if (stmt.getResultSet() != null && stmt.getResultSet().isBeforeFirst()) {
                setRowsAvailable(true);
                setResultSet(stmt.getResultSet());
            }
            if (isRowsAvailable){
                parseResultSet();
            }
            stmt.getMoreResults();
            setReturnCode(stmt.getInt(1));
            parseOutputVars();
        } catch (Exception e) {
            setException(e);
            e.printStackTrace();
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return !isDoError;
    }



    protected void parseResultSet() throws Exception {

    }

    protected void parseOutputVars() throws Exception {

    }



    /**********************************************************************************************/


    /*public interface IAsyncQueryEventListener {
        void onAsyncQueryEvent(CallableQAsync query, int returnCode, String returnMessage);

    }

    private List<CallableQAsync.IAsyncQueryEventListener> asyncQueryEventListenerList;
    public void addAsyncQueryEventListener(CallableQAsync.IAsyncQueryEventListener listener) {
        if (asyncQueryEventListenerList == null) {
            asyncQueryEventListenerList = new ArrayList<>();
        }
        if (!asyncQueryEventListenerList.contains(listener)) {
            asyncQueryEventListenerList.add(listener);
        }
    }

    public void removeAsyncQueryEventListener(CallableQAsync.IAsyncQueryEventListener listener) {
        if (asyncQueryEventListenerList == null) {
            return;
        }
        if (asyncQueryEventListenerList.contains(listener)) {
            asyncQueryEventListenerList.remove(listener);
        }
    }*/


    /**********************************************************************************************/


    /*public interface IAsyncQueryExceptionListener {
        void onAsyncQueryException(CallableQAsync query,Exception e);
    }

    private List<CallableQAsync.IAsyncQueryExceptionListener> asyncQueryExceptionListenerList;
    public void addAsyncExceptionEventListener(CallableQAsync.IAsyncQueryExceptionListener listener) {
        if (asyncQueryExceptionListenerList == null) {
            asyncQueryExceptionListenerList = new ArrayList<>();
        }
        if (!asyncQueryExceptionListenerList.contains(listener)) {
            asyncQueryExceptionListenerList.add(listener);
        }
    }

    public void removeAsyncvEventListener(CallableQAsync.IAsyncQueryExceptionListener listener) {
        if (asyncQueryExceptionListenerList == null) {
            return;
        }
        if (asyncQueryExceptionListenerList.contains(listener)) {
            asyncQueryExceptionListenerList.remove(listener);
        }
    }*/


    /**********************************************************************************************/

    /*public interface OnPreExecuteListener {
        void onPreExecute();
    }


    private List<OnPreExecuteListener> onPreExecuteListenerList;

    public void addOnPreExecuteListener(OnPreExecuteListener pre) {
        if (onPreExecuteListenerList == null) {
            onPreExecuteListenerList = new ArrayList<>();
        }

        if (!onPreExecuteListenerList.contains(pre)) {
            onPreExecuteListenerList.add(pre);
        }
    }

    public void removeOnPreExecuteListener(OnPreExecuteListener pre) {
        if (onPreExecuteListenerList == null) {
            return;
        }
        if (onPreExecuteListenerList.contains(pre)) {
            onPreExecuteListenerList.remove(pre);
        }
    }*/


    /**********************************************************************************************/

    /*public interface OnPostExecuteListener {
        void onPostExecute();
    }

    private List<OnPostExecuteListener> onPostExecuteListenerList;

    public void addOnPostExecuteListener(OnPostExecuteListener post) {
        if (onPostExecuteListenerList == null) {
            onPostExecuteListenerList = new ArrayList<>();
        }

        if (!onPostExecuteListenerList.contains(post)) {
            onPostExecuteListenerList.add(post);
        }
    }

    public void removeOnPostExecuteListener(OnPostExecuteListener post) {
        if (onPostExecuteListenerList == null) {
            return;
        }
        if (onPostExecuteListenerList.contains(post)) {
            onPostExecuteListenerList.remove(post);
        }
    }*/


    /**********************************************************************************************/


    /*public interface OnProgressUpdateListener{
        void onProgressUpdate(String... values);
    }

    private List<OnProgressUpdateListener> onProgressUpdateListenerList;

    public void addOnProgressUpdateListener(OnProgressUpdateListener progress) {
        if (onProgressUpdateListenerList == null) {
            onProgressUpdateListenerList = new ArrayList<>();
        }
        if (!onProgressUpdateListenerList.contains(progress)) {
            onProgressUpdateListenerList.add(progress);
        }
    }

    public void removeOnProgressUpdateListener(OnProgressUpdateListener progress) {
        if (onProgressUpdateListenerList == null) {
            return;
        }
        if (onProgressUpdateListenerList.contains(progress)) {
            onProgressUpdateListenerList.remove(progress);
        }
    }*/


    /**********************************************************************************************/


    /*public interface OnCancelledListener {
        void onCancelled();
    }

    private List<OnCancelledListener> onCancelledListenerList;

    public void addOnCancelledListener(OnCancelledListener canceled) {
        if (onCancelledListenerList == null) {
            onCancelledListenerList = new ArrayList<>();
        }
        if (!onCancelledListenerList.contains(canceled)) {
            onCancelledListenerList.add(canceled);
        }
    }

    public void removeOnCancelledListener(OnCancelledListener canceled) {
        if (onCancelledListenerList == null) {
            return;
        }
        if (onCancelledListenerList.contains(canceled)) {
            onCancelledListenerList.remove(canceled);
        }
    }*/


    /**********************************************************************************************/


    /*public interface OnSuccessfulListener {
        void onSuccessful();
    }

    private List<OnSuccessfulListener> onSuccessfulListenerList;

    public void addOnSuccessfulListener(OnSuccessfulListener canceled) {
        if (onSuccessfulListenerList == null) {
            onSuccessfulListenerList = new ArrayList<>();
        }
        if (!onSuccessfulListenerList.contains(canceled)) {
            onSuccessfulListenerList.add(canceled);
        }
    }

    public void removeOnSuccessfulListener(OnSuccessfulListener canceled) {
        if (onSuccessfulListenerList == null) {
            return;
        }
        if (onSuccessfulListenerList.contains(canceled)) {
            onSuccessfulListenerList.remove(canceled);
        }
    }

    protected void sendOnSuccessfulListener() {
        if (onSuccessfulListenerList != null) {
            for (CallableQAsync.OnSuccessfulListener listener : onSuccessfulListenerList) {
                listener.onSuccessful();
            }
        }
    }*/


    /**********************************************************************************************/



    /**
     * Флаг показывает, что этот запрос запустился асинхронно
     * */
    private boolean isRunAsync;
    public boolean isRunAsync() {
        return isRunAsync;
    }

    protected void setRunAsync(boolean runAsync) {
        isRunAsync = runAsync;
    }

    public void cancel() {
        if (task != null) {
            task.cancel(true);
        }
    }


    /******************************************************/
    /******     !!!!!!!!!!!!   ВОТ ОН TASK !!!!!!!!!!!!!! */
    /******************************************************/

    private class DbTask extends AsyncTask<Void, String, Void> {


        public void publishIt(String... message) {
            publishProgress(message);
        }

        @Override
        protected Void doInBackground(Void... params) {
            CallableQAsync.this.Execute();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            CallableQAsync.this.setRunning(true);
            setRunAsync(true);

            if (onPreExecuteListenerList != null && onPreExecuteListenerList.size() > 0) {
                for (OnPreExecuteListener onPreExecuteListener : onPreExecuteListenerList) {
                    onPreExecuteListener.onPreExecute();
                }
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            CallableQAsync.this.setRunning(false);

            if (onPostExecuteListenerList != null && onPostExecuteListenerList.size() > 0) {
                for (OnPostExecuteListener onPostExecuteListener : onPostExecuteListenerList)
                    onPostExecuteListener.onPostExecute();
            }

            if (isDoError()) {
                sendException(CallableQAsync.this,getException());
            } else {
                sendEvent(CallableQAsync.this,returnCode,eventMessage);
                sendOnSuccessfulListener();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if (onProgressUpdateListenerList != null && onProgressUpdateListenerList.size() > 0) {
                for (OnProgressUpdateListener onProgressUpdateListener : onProgressUpdateListenerList) {
                    onProgressUpdateListener.onProgressUpdate(values);
                }
            }
        }

        @Override
        protected void onCancelled(Void result) {
            super.onCancelled(result);
            CallableQAsync.this.setRunning(false);
            if (onCancelledListenerList != null && onCancelledListenerList.size() > 0) {
                for (OnCancelledListener onCancelledListener : onCancelledListenerList) {
                    onCancelledListener.onCancelled();
                }
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            CallableQAsync.this.setRunning(false);
            if (onCancelledListenerList != null && onCancelledListenerList.size() > 0) {
                for (OnCancelledListener onCancelledListener : onCancelledListenerList) {
                    onCancelledListener.onCancelled();
                }
            }
        }
    }
}
