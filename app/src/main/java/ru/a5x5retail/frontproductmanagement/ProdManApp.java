package ru.a5x5retail.frontproductmanagement;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.Map;

import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.packinglistpreview.PackingListPreviewActivity;

import static ru.a5x5retail.frontproductmanagement.configuration.Constants.PACKINGLISTHEAD_CONST;
import static ru.a5x5retail.frontproductmanagement.configuration.Constants.TYPEOFDOCUMENT_CONST;

public class ProdManApp extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        ProdManApp.context = getApplicationContext();
       // StartMainActivity();
    }

    public static Context getAppContext() {
        return context;
    }

    public static class Toastss{
        public static void MakeToast(String text){
            Toast toast = Toast.makeText(getAppContext(),
                    text, Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public static class Alerts {

        public static void MakeErrorAlert(Context context,Exception e,String text) {
            if (AppConfigurator.isDebug()) {
                StringBuilder sb = new StringBuilder();
                if (e == null) {

                } else {
                    e.printStackTrace();
                    sb.append(e.getMessage());
                    sb.append(System.getProperty("line.separator"));
                    sb.append(System.getProperty("line.separator"));
                    StackTraceElement[] st = e.getStackTrace();
                    if (st == null) {
                    } else {
                        for (StackTraceElement stackTraceElement : st) {
                            sb.append("*");
                            sb.append(stackTraceElement.toString());
                            sb.append(System.getProperty("line.separator"));
                            sb.append(System.getProperty("line.separator"));
                        }
                    }
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(sb.toString());
                builder.setNeutralButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            } else {
                Toast toast = Toast.makeText(getAppContext(),
                        text, Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        public static void MakeToast(String text, int toast_Len){
            Toast toast = Toast.makeText(getAppContext(),
                    text, toast_Len);
            toast.show();
        }
    }

    public static class Activities {


        public static void createPackingListPreviewActivity( Context context, Parcelable head,int typeDocIndex){
            Intent intent = new Intent(context, PackingListPreviewActivity.class);
            intent.putExtra(PACKINGLISTHEAD_CONST,head);
            intent.putExtra(TYPEOFDOCUMENT_CONST,typeDocIndex);
            context.startActivity(intent);
        }

        public static void createNewDocumentActivity(FragmentActivity context, Class<?> activityClass, int typeDocIndex, int requestCode){
            Intent intent = new Intent(context, activityClass);
            intent.putExtra(TYPEOFDOCUMENT_CONST,typeDocIndex);
            context.startActivityForResult(intent,requestCode);

        }

        /*public static final void StartActivity (Context context,Class<?> activityClass){
            Intent intent = new Intent(context,activityClass);
            context.startActivity(intent);
        }

        public static final void StartActivity (Context context, Class<?> activityClass, Map<String, ?> extraMap){
            Intent intent = new Intent(context,activityClass);
            for (Map.Entry<String, ?> stringObjectEntry : extraMap.entrySet()) {
               String key = stringObjectEntry.getKey();
               Object value = stringObjectEntry.getValue();
               if (value instanceof Integer) {
                   intent.putExtra(key,(Integer)value);
               } else {
                   intent.putExtra(key,value.toString());
               }
            }
            context.startActivity(intent);
        }*/
    }

    public void StartMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
