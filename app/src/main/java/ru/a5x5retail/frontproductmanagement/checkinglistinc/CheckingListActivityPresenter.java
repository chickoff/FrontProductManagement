package ru.a5x5retail.frontproductmanagement.checkinglistinc;

import com.arellomobile.mvp.InjectViewState;

import ru.a5x5retail.frontproductmanagement.base.BasePresenter;


@InjectViewState
public class CheckingListActivityPresenter extends BasePresenter<ICheckingListActivityView> {

    public static final String TAG = "CheckingListActivityPresenter";


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        showPositions();
    }

    public void showPositions() {
        getViewState().showPositions();
    }
    public void showManufactor(){
        getViewState().showManufactor();
    }
    public void showMarks(){
        getViewState().showMarks();
    }

}
