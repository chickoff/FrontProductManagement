package ru.a5x5retail.frontproductmanagement;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db_local.DatabaseInit;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.creators.newinvoice.CreateNewInvoiceActivity;
import ru.a5x5retail.frontproductmanagement.packinglistpreview.view.PackingListPreviewActivity;

import static ru.a5x5retail.frontproductmanagement.configuration.Constants.CONTRACTOR_INFO_CONST;

import static ru.a5x5retail.frontproductmanagement.configuration.Constants.PLAN_INCOME_CONST;

import static ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.creators.newinvoice.CreateNewInvoiceActivity.BASIS_OF_CREATION;


public class ProdManApp extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        ProdManApp.context = getApplicationContext();
        Thread.UncaughtExceptionHandler defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(defaultUEH));
        DatabaseInit.InitDao(this);
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

        private static void createAlert(String msg) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getAppContext());
            builder.setMessage(msg);
            builder.setNeutralButton("ОК", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.create().show();
        }

        public static void MakeErrorAlert(Context context, Exception e, String text) {
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
                createAlert(sb.toString());
            } else {
                Toast toast = Toast.makeText(getAppContext(),
                        text, Toast.LENGTH_LONG);
                toast.show();
            }
        }

        public static void MakeToast(String text, int toast_Len){

            if (AppConfigurator.isDebug()) {
                createAlert(text);
            } else {
                Toast toast = Toast.makeText(getAppContext(),
                        text, toast_Len);
                toast.show();
            }
        }

        public static void MakeAlertVibrate() {
            Vibrator v = (Vibrator) getAppContext().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(300);
        }

        public static void MakeDoubleVibrate() {
            Vibrator v = (Vibrator) getAppContext().getSystemService(Context.VIBRATOR_SERVICE);

            long[] i = {100,200,300,200};
            v.vibrate(i,3);
        }
    }

    public static class Activities {


        public static void createPackingListPreviewActivity( Context context){
            Intent intent = new Intent(context, PackingListPreviewActivity.class);
            context.startActivity(intent);
        }

        public static void createNewInvoiceActivity( FragmentActivity context,int basisOfCreation, Parcelable contractorInfo,
                          Parcelable planIncome, int requestCode){
            Intent intent = new Intent(context, CreateNewInvoiceActivity.class);
            intent.putExtra(CONTRACTOR_INFO_CONST,contractorInfo);
            intent.putExtra(PLAN_INCOME_CONST,planIncome);
            intent.putExtra(BASIS_OF_CREATION,basisOfCreation);
            context.startActivityForResult(intent,requestCode);
        }

        public static void createNewDocumentActivity(FragmentActivity context, Class<?> activityClass, int requestCode){
            Intent intent = new Intent(context, activityClass);
            context.startActivityForResult(intent,requestCode);

        }


        public static void createNewDocumentMasterActivity(FragmentActivity context, Class<?> activityClass, Constants.TypeOfDocument typeOfDoc, int requestCode){
            Intent intent = new Intent(context, activityClass);
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

    public static void exceptionToFile(Throwable ex) {
        String stackTrace = Log.getStackTraceString(ex);
        String time = new Date(System.currentTimeMillis()).toString();
        String message = ex.getMessage();

        String filename = "stack_trace_" + String.valueOf(System.currentTimeMillis()) + ".html";
        StringBuilder sb = new StringBuilder();
        sb.append("<handlederror>");
        sb.append("<ver>");
        sb.append(AppConfigurator.GetCurrentVersion());
        sb.append("</ver>");
        sb.append("<stacktrace>");
        sb.append("<![CDATA[");
        sb.append(stackTrace);
        sb.append("]]>");
        sb.append("</stacktrace>");
        sb.append("</handlederror>");
        FileOutputStream outputStream;

        try {
            File dir = ProdManApp.getAppContext().getExternalFilesDir("events");
            File outputFile = new File(dir, filename);
            outputStream = new FileOutputStream(outputFile);
            outputStream.write(sb.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
