package ru.a5x5retail.frontproductmanagement.base;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;


public class BaseAppCompatActivity extends AppCompatActivity {

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
}
