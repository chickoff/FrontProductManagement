package ru.a5x5retail.frontproductmanagement.base;

import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import ru.a5x5retail.frontproductmanagement.configuration.Constants;

public class TypedViewModel extends BaseViewModel
implements Observable
{
    private transient PropertyChangeRegistry mCallbacks;

    private Constants.TypeOfDocument typeOfDoc;

    private boolean isInitialized = false;

    public Constants.TypeOfDocument getTypeOfDoc() {
        return typeOfDoc;
    }

    public void setTypeOfDoc(Constants.TypeOfDocument typeOfDoc) {
        this.typeOfDoc = typeOfDoc;
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                mCallbacks = new PropertyChangeRegistry();
            }
        }
        mCallbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.remove(callback);
    }

    public void notifyPropertyChanged(int fieldId) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.notifyCallbacks(this, fieldId, null);
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }
}
