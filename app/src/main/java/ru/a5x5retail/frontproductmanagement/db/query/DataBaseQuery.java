package ru.a5x5retail.frontproductmanagement.db.query;

import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.base.IDataBaseQueryEvent;

public class DataBaseQuery
        implements IDataBaseQueryEvent
{
    public interface OnPreExecuteListener {
        void onPreExecute();
    }


    protected List<OnPreExecuteListener> onPreExecuteListenerList;
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
    }

    protected void sendOnPreExecute() {
        if (onPreExecuteListenerList != null) {
            for (OnPreExecuteListener listener : onPreExecuteListenerList) {
                listener.onPreExecute();
            }
        }
    }

    /**********************************************************************************************/


    public interface OnPostExecuteListener {
        void onPostExecute();
    }

    protected List<OnPostExecuteListener> onPostExecuteListenerList;
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
    }

    protected void sendOnPostExecute() {
        if (onPostExecuteListenerList != null) {
            for (OnPostExecuteListener listener : onPostExecuteListenerList) {
                listener.onPostExecute();
            }
        }
    }

    /**********************************************************************************************/


    public interface OnProgressUpdateListener{
        void onProgressUpdate(String... values);
    }

    protected List<OnProgressUpdateListener> onProgressUpdateListenerList;

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
    }

    protected void sendOnProgressUpdate(String... values) {
        if (onProgressUpdateListenerList != null) {
            for (OnProgressUpdateListener listener : onProgressUpdateListenerList) {
                listener.onProgressUpdate();
            }
        }
    }


    /**********************************************************************************************/


    public interface OnCancelledListener {
        void onCancelled();
    }

    protected List<OnCancelledListener> onCancelledListenerList;

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
    }

    protected void sendOnCancelled() {
        if (onCancelledListenerList != null) {
            for (OnCancelledListener listener : onCancelledListenerList) {
                listener.onCancelled();
            }
        }
    }


    /**********************************************************************************************/


    public interface IAsyncQueryEventListener {
        void onAsyncQueryEvent(CallableQAsync query, int returnCode, String returnMessage);

    }

    protected List<IAsyncQueryEventListener> asyncQueryEventListenerList;
    public void addAsyncQueryEventListener(IAsyncQueryEventListener listener) {
        if (asyncQueryEventListenerList == null) {
            asyncQueryEventListenerList = new ArrayList<>();
        }
        if (!asyncQueryEventListenerList.contains(listener)) {
            asyncQueryEventListenerList.add(listener);
        }
    }

    public void removeAsyncQueryEventListener(IAsyncQueryEventListener listener) {
        if (asyncQueryEventListenerList == null) {
            return;
        }
        if (asyncQueryEventListenerList.contains(listener)) {
            asyncQueryEventListenerList.remove(listener);
        }
    }

    protected void sendAsyncQueryEvent(CallableQAsync query, int returnCode, String returnMessage) {
        if (asyncQueryEventListenerList != null) {
            for (IAsyncQueryEventListener listener : asyncQueryEventListenerList) {
                listener.onAsyncQueryEvent(query,returnCode,returnMessage);
            }
        }
    }

    /**********************************************************************************************/


    public interface IAsyncQueryExceptionListener {
        void onAsyncQueryException(CallableQAsync query,Exception e);
    }

    protected List<IAsyncQueryExceptionListener> asyncQueryExceptionListenerList;
    public void addAsyncExceptionEventListener(IAsyncQueryExceptionListener listener) {
        if (asyncQueryExceptionListenerList == null) {
            asyncQueryExceptionListenerList = new ArrayList<>();
        }
        if (!asyncQueryExceptionListenerList.contains(listener)) {
            asyncQueryExceptionListenerList.add(listener);
        }
    }

    public void removeAsyncvEventListener(IAsyncQueryExceptionListener listener) {
        if (asyncQueryExceptionListenerList == null) {
            return;
        }
        if (asyncQueryExceptionListenerList.contains(listener)) {
            asyncQueryExceptionListenerList.remove(listener);
        }
    }

    protected void sendAsyncQueryException(CallableQAsync query,Exception e) {
        if (asyncQueryExceptionListenerList != null) {
            for (IAsyncQueryExceptionListener listener : asyncQueryExceptionListenerList) {
                listener.onAsyncQueryException(query,e);
            }
        }
    }


    /**********************************************************************************************/


    public interface OnSuccessfulListener {
        void onSuccessful();
    }

    protected List<OnSuccessfulListener> onSuccessfulListenerList;
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
            for (OnSuccessfulListener listener : onSuccessfulListenerList) {
                listener.onSuccessful();
            }
        }
    }


    /**********************************************************************************************/
}
