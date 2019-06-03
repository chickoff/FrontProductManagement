package ru.a5x5retail.frontproductmanagement;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;

public class CustomExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final Thread.UncaughtExceptionHandler defaultUEH;

    public CustomExceptionHandler(Thread.UncaughtExceptionHandler defaultUEH) {
        this.defaultUEH = defaultUEH;
    }
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        String stackTrace = Log.getStackTraceString(ex);
        String time = new Date(System.currentTimeMillis()).toString();
        String message = ex.getMessage();

        String filename = "stack_trace_" + String.valueOf(System.currentTimeMillis()) + ".html";
        StringBuilder sb = new StringBuilder();
        sb.append("<error>");
        sb.append("<ver>");
        sb.append(AppConfigurator.GetCurrentVersion());
        sb.append("</ver>");
        sb.append("<stacktrace>");
        sb.append("<![CDATA[");
        sb.append(stackTrace);
        sb.append("]]>");
        sb.append("</stacktrace>");
        sb.append("</error>");
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

        defaultUEH.uncaughtException(thread, ex);
    }
}