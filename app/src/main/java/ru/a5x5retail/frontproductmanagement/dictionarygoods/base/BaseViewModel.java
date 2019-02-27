package ru.a5x5retail.frontproductmanagement.dictionarygoods.base;

import android.arch.lifecycle.ViewModel;

import ru.a5x5retail.frontproductmanagement.dictionarygoods.configuration.Constants;


public class BaseViewModel extends ViewModel {

    private Constants.ViewModelStateEnum state;
    public BaseViewModel() {
        state = Constants.ViewModelStateEnum.NEW;
    }

    public void Load(){
        state = Constants.ViewModelStateEnum.LOADED;
    }

    public Constants.ViewModelStateEnum getState() {
        return state;
    }
}
