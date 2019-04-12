package ru.a5x5retail.frontproductmanagement.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ru.a5x5retail.frontproductmanagement.MainActivity;
import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.updateapplication.UpdateApplicationActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class BaseAppCompatActivity extends AppCompatActivity {



    private String STATE_ACTIVITY = "STATE_ACTIVITY";
    private int STATE_ACTIVITY_VALUE = 0;
     private static boolean firstStart = true;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUpdateVer();
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

    private void checkUpdateVer() {
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
    }






}
