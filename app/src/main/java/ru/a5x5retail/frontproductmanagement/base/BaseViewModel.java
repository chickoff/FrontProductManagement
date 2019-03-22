package ru.a5x5retail.frontproductmanagement.base;

import android.arch.lifecycle.ViewModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.configuration.Constants;

public class BaseViewModel extends ViewModel

{


    private Constants.ViewModelStateEnum state;

    public BaseViewModel() {
        state = Constants.ViewModelStateEnum.NEW;
        dataChangedList = new ArrayList<>();
        //listenerChangedListeners = new ArrayList<>();

    }

    public void Load() throws SQLException, ClassNotFoundException {
        state = Constants.ViewModelStateEnum.LOADED;
    }

    public Constants.ViewModelStateEnum getState() {
        return state;
    }


    /**
     *
     *
     *
     */


    private List<IDataChangedListener> dataChangedList;

    public void addDataChangedListener(IDataChangedListener listener) {
        if (dataChangedList == null) return;
        dataChangedList.add(listener);
        //onListenerIsAdd(listener);
    }

    public void removeDataChangedListener(IDataChangedListener listener) {
        if (dataChangedList == null) return;
        dataChangedList.remove(listener);
        //onlistenerIsRemove(listener);
    }

    protected void onDataChanged() {
        if (dataChangedList == null || dataChangedList.size() == 0 ) return;
        for (IDataChangedListener iDataChanged : dataChangedList) {
            if (iDataChanged != null) {
                iDataChanged.dataIsChanged();
            }
        }
    }


/**
 *
 *
 *
 */


    /*private List<IListenerChangedListener> listenerChangedListeners;

    public void registerListenerChangedListener(IListenerChangedListener listener) {
        if (listenerChangedListeners == null) return;
        listenerChangedListeners.add(listener);

    }

    public void unregisterListenerChangedListener(IListenerChangedListener listener) {
        if (listenerChangedListeners == null) return;
        listenerChangedListeners.remove(listener);
    }

    private void onListenerIsAdd(IDataChangedListener listener) {
        if (listenerChangedListeners == null || listenerChangedListeners.size() == 0 ) return;
        for (IListenerChangedListener listenerChangedListener : listenerChangedListeners) {
            if (listenerChangedListener!=null) {
                listenerChangedListener.listenerIsAdd(listener);
            }
        }
    }

    private void onlistenerIsRemove(IDataChangedListener listener) {
        if (listenerChangedListeners == null || listenerChangedListeners.size() == 0 ) return;
        for (IListenerChangedListener listenerChangedListener : listenerChangedListeners) {
            if (listenerChangedListener!=null) {
                listenerChangedListener.listenerIsRemove(listener);
            }
        }
    }*/
/**
 *
 *
 *
 */

    public interface IDataChangedListener {
         void dataIsChanged();
    }

   /* public interface  IListenerChangedListener{
        void listenerIsAdd(IDataChangedListener listener);
        void listenerIsRemove(IDataChangedListener listener);
    }*/
}
