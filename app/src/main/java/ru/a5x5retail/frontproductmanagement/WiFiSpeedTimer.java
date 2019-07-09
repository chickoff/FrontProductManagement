package ru.a5x5retail.frontproductmanagement;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class WiFiSpeedTimer {

    private Activity activity;
    public WiFiSpeedTimer(Activity activity) {
        this.activity = activity;
    }

    public void StartTimer() {
        Timer mTimer = new Timer();
        mTimer.schedule(new MyTimerTask(),5000, 5000);
    }


    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            WifiManager wifiManager = (WifiManager) ProdManApp.getAppContext().getApplicationContext().getSystemService (Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo ();
            int speed = wifiInfo.getLinkSpeed();
            Log.d("Speed",String.valueOf(speed));
            final String warning = "Внимание! Низкий сигнал WiFi: " + String.valueOf(speed);
            final String alarm = "Связь отсутствует полностью!" + String.valueOf(speed);
            if (speed <= 5 && speed >=0) {
                activity.runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                ProdManApp.Alerts.MakeToast(null,warning, Toast.LENGTH_SHORT);
                            }
                        }
                );
            }

            if (speed < 0 ) {
                activity.runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                ProdManApp.Alerts.MakeToast(null,alarm, Toast.LENGTH_SHORT);
                            }
                        }
                );
            }
        }
    }
}
