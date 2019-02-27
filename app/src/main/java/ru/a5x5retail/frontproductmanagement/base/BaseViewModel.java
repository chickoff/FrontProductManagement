package ru.a5x5retail.frontproductmanagement.base;

import android.arch.lifecycle.ViewModel;

import java.sql.SQLException;

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
}
