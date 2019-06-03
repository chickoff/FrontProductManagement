package ru.a5x5retail.frontproductmanagement.checkinglistinc;

import java.lang.ref.WeakReference;

import ru.a5x5retail.frontproductmanagement.checkinglistinc.fragments.CheckingListIncPresenter;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;

public class CheckingListIncPresenterBridge {


    public static WeakReference<CheckingListIncPresenter> checkingListIncPresenterWeakReference;

    public static void sendSelectedSkuInfo(CodeInfo codeInfo) {
        checkingListIncPresenterWeakReference.get().addNewSku(codeInfo.code);
    }
}
