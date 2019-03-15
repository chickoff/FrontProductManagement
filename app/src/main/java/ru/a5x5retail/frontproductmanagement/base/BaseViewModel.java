package ru.a5x5retail.frontproductmanagement.base;

import android.arch.lifecycle.ViewModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.configuration.Constants;

public class BaseViewModel extends ViewModel {


    private Constants.ViewModelStateEnum state;
    public BaseViewModel() {
        state = Constants.ViewModelStateEnum.NEW;
    }

    public void Load() throws SQLException, ClassNotFoundException {
        state = Constants.ViewModelStateEnum.LOADED;
    }

    public Constants.ViewModelStateEnum getState() {
        return state;
    }

    private List<IDataChanged> dataChangedList;

    public void addDataChangedListener(IDataChanged listener) {
        if (dataChangedList == null) {
            dataChangedList = new ArrayList<>();
        }
        dataChangedList.add(listener);
    }

    public void removeDataChangedListener(IDataChanged listener) {
        if (dataChangedList == null) return;
        dataChangedList.remove(listener);
    }

    protected void onDataChanged() {
        if (dataChangedList == null || dataChangedList.size() == 0 ) return;
        for (IDataChanged iDataChanged : dataChangedList) {
            if (iDataChanged != null) {
                iDataChanged.dataIsChanged();
            }
        }
    }


    public interface IDataChanged {
         void dataIsChanged();
    }
}
