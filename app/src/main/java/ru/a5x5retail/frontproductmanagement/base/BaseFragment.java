package ru.a5x5retail.frontproductmanagement.base;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.arellomobile.mvp.MvpAppCompatFragment;

import java.lang.ref.WeakReference;

import ru.a5x5retail.frontproductmanagement.dialogs.working.ProcessFragmentDialog;
import ru.a5x5retail.frontproductmanagement.interfaces.IBaseMvpView;

public class BaseFragment extends MvpAppCompatFragment
    implements IBaseMvpView

{




    private String STATE_FRAGMENT = "STATE_FRAGMENT";
    private int STATE_FRAGMENT_VALUE = 0;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            STATE_FRAGMENT_VALUE = savedInstanceState.getInt(STATE_FRAGMENT);
        }

    }


    private static boolean firstStart = true;
    public static boolean isFirstStart() {
        return firstStart;
    }

    public static void setFirstStart(boolean firstStart) {
        BaseFragment.firstStart = firstStart;
    }


    private WeakReference<IBaseMvpView> mvpActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //FragmentActivity activity = getActivity();
        if (context instanceof IBaseMvpView) {
            mvpActivity = new WeakReference(context);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (STATE_FRAGMENT_VALUE == 0){
            onFirstInit();
            STATE_FRAGMENT_VALUE = 1;
        }



    }


    public void onKeyUp(int keyCode, KeyEvent event) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (getActivity().isChangingConfigurations()) {
            firstStart = false;
        }  else {
            firstStart = true;
        }


    }

    protected void onFirstInit() {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState == null) return;
        outState.putInt(STATE_FRAGMENT,STATE_FRAGMENT_VALUE);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        firstStart = false;
    }



    ProcessFragmentDialog processDialog;

    public ProcessFragmentDialog getProcessDialog() {
        if (processDialog == null){
            processDialog = new ProcessFragmentDialog();
        }
        return processDialog;
    }

    public void showAwaitDialog(boolean show){
        if (mvpActivity != null) {
            mvpActivity.get().showAwaitDialog(show);
        }


        /*if (show) {
            if (!getProcessDialog().isAdded()) {
                getProcessDialog().show(getActivity().getSupportFragmentManager(),"eee");
            }

        } else {

                                                boolean a = getProcessDialog().isAdded();
                                                boolean b = getProcessDialog().isCancelable();
                                                boolean c = getProcessDialog().isDetached();
                                                boolean d = getProcessDialog().isHidden();
                                                boolean e = getProcessDialog().isInLayout();
            @SuppressLint("RestrictedApi")      boolean f = getProcessDialog().isMenuVisible();
                                                boolean j = getProcessDialog().isRemoving();
                                                boolean k = getProcessDialog().isResumed();
                                                boolean l = getProcessDialog().isStateSaved();
                                                boolean m = getProcessDialog().isVisible();

            if (getProcessDialog().isAdded()) {
                getProcessDialog().dismiss();
            }
        }*/
    }

    @Override
    public void showEventToast(String text, int toast_Len) {
        mvpActivity.get().showEventToast(text,toast_Len);
    }

    @Override
    public void showExceptionToast(Exception e, String text) {
        mvpActivity.get().showExceptionToast(e,text);
    }


}
