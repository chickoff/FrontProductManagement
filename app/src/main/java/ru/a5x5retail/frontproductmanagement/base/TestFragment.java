package ru.a5x5retail.frontproductmanagement.base;

import android.support.v4.app.Fragment;

import com.arellomobile.mvp.MvpAppCompatFragment;

import ru.a5x5retail.frontproductmanagement.base.BaseViewModel;

public abstract class TestFragment<T extends BaseViewModel> extends BaseFragment {
    private T viewModel;

    public T getViewModel() {
        return viewModel;
    }

    public void setViewModel(T viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onResume() {
        super.onResume();
        registerChangedListener();
        registerListener();

    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterChangedListener();
        unregisterListener();

    }

    private void registerListener() {
        if (viewModel == null) return;
        viewModel.addDataChangedListener(dataChangedSub);
    }

    private void unregisterListener() {
        if (viewModel == null) return;
        viewModel.removeDataChangedListener(dataChangedSub);


    }

    private void registerChangedListener() {
        listenerChangedListenerAdded();
    }

    private void unregisterChangedListener() {
        listenerChangedListenerRemove();
    }


    BaseViewModel.IDataChangedListener dataChangedSub = new BaseViewModel.IDataChangedListener() {
        @Override
        public void dataIsChanged() {
            viewModelDataIsChanged();
        }
    };


    protected abstract void viewModelDataIsChanged();

    public abstract void listenerChangedListenerRemove();

    public abstract void listenerChangedListenerAdded();

}
