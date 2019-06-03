package ru.a5x5retail.frontproductmanagement.base;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;

import java.lang.ref.ReferenceQueue;

import ru.a5x5retail.frontproductmanagement.MainActivity;
import ru.a5x5retail.frontproductmanagement.ProdManApp;

import ru.a5x5retail.frontproductmanagement.dialogs.working.ProcessFragmentDialog;
import ru.a5x5retail.frontproductmanagement.interfaces.IBaseMvpView;
import ru.a5x5retail.frontproductmanagement.updateapplication.UpdateApplicationActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


@SuppressLint("Registered")
public class BaseAppCompatActivity extends MvpAppCompatActivity
implements IAttachProcessFragmentDialog, IBaseMvpView
{

public BaseAppCompatActivity() {
    Log.d("DataBaseQuery",this.toString());
}

    private String STATE_ACTIVITY = "STATE_ACTIVITY";
    private int STATE_ACTIVITY_VALUE = 0;
     private static boolean firstStart = true;



     protected void Test(){

     }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //checkUpdateVer();
        Test();


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("STATE_ACTIVITY", STATE_ACTIVITY_VALUE);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState == null) return;
        STATE_ACTIVITY_VALUE = savedInstanceState.getInt("STATE_ACTIVITY", STATE_ACTIVITY_VALUE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (STATE_ACTIVITY_VALUE == 0){
            firstInit();
            STATE_ACTIVITY_VALUE = 1;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (isChangingConfigurations()) {
            firstStart = false;
        }

        if (isFinishing()) {
            firstStart = true;
            finishingActivity();
        }
    }

    private void finishingActivity() {

    }


    protected void firstInit() {

    }

    protected boolean isFirstStart() {
         return STATE_ACTIVITY_VALUE == 0;

                 //firstStart;
    }


    public void setErrorToast(Exception e, String text){

        ProdManApp.Alerts.MakeErrorAlert(this,e,text);

        /*if (AppConfigurator.isDebug()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(e.getStackTrace().toString());
            builder.setNeutralButton("ОК", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

        } else {
            Toast toast = Toast.makeText(this,
                    text, Toast.LENGTH_SHORT);
            toast.show();
        }*/


    }

    /*private void checkUpdateVer() {
        int isNewVersion = -1;
        isNewVersion = AppConfigurator.checkNewVersion();
        switch (isNewVersion) {
            case 1:
                Intent updIntent = new Intent(BaseAppCompatActivity.this, UpdateApplicationActivity.class);
                updIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                startActivity(updIntent);
                return;
            case -1:
                ProdManApp.Alerts.MakeToast("Нет связи с сервером обновлений. Работа с ТСД приостановлена.", Toast.LENGTH_LONG);
                return;
            case 0:
        }
    }*/


    public ProcessFragmentDialog getProcessDialog() {
        if (processDialog == null){
            processDialog = new ProcessFragmentDialog();
        }
        processDialog.setCancelListener(new ProcessFragmentDialog.IProcessDialogCancelListener() {
            @Override
            public void onCancel() {
                dffdsfdfdf();
            }
        });
        return processDialog;
    }

    protected void dffdsfdfdf() {
        int a = 2;
    }

    ProcessFragmentDialog processDialog;

    private FragmentTransaction getFragmentTransaction() {
       return getSupportFragmentManager().beginTransaction();
    }


    private boolean isShowAwaitDialog;
    @Override
    public void showAwaitDialog(boolean show){
       // dffdsfdfdf();

        if (!isShowAwaitDialog && show) {
            getProcessDialog().show(getSupportFragmentManager(),"eee");
            isShowAwaitDialog = show;
        } else if (isShowAwaitDialog && !show){
            getProcessDialog().dismissAllowingStateLoss();
            isShowAwaitDialog = show;
        }

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


    /*    if (show) {





            if (!getProcessDialog().isAdded()) {
                //FragmentTransaction tra = getFragmentTransaction();
                getProcessDialog().show(getSupportFragmentManager(),"eee");
                return;
            }

            if (getProcessDialog().isAdded() && !getProcessDialog().isVisible()) {
                getProcessDialog().show(getSupportFragmentManager(),"eee");
                //getProcessDialog().setShowsDialog(true);
                return;
            }

        } else {
            if (getProcessDialog().isAdded()) {
                getProcessDialog().dismissAllowingStateLoss();
            }
        }*/
    }

    @Override
    public void showEventToast(String text, int toast_Len) {
        ProdManApp.Alerts.MakeToast(text,toast_Len);
    }

    @Override
    public void showExceptionToast(Exception e, String text) {
        ProdManApp.Alerts.MakeErrorAlert(this,e,e.getMessage());
    }


    @Override
    public void attachProcessDialog(ProcessFragmentDialog dialog) {
        processDialog = dialog;
    }



}
