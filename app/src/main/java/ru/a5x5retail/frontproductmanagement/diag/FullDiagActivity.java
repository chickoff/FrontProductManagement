package ru.a5x5retail.frontproductmanagement.diag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.TestConnectionQuery;

public class FullDiagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_diag);
        initUi();
        updateUi();
    }


    private TextView wifi_ssid_text_view, ip_addr_text_view,ping_text_view,db_text_view,
            server_ip_addr_text_view,update_text_view;
    private Runnable run0,run1,run2;
    private Thread thread0,thread1,thread2;
    private Button updateButton;
    private ProgressBar ping_progress,db_progress,update_progress;



    @SuppressLint("RestrictedApi")
    private void initUi() {

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        wifi_ssid_text_view = findViewById(R.id.wifi_ssid_text_view);
        ip_addr_text_view = findViewById(R.id.ip_addr_text_view);
        ping_text_view = findViewById(R.id.ping_text_view);
        db_text_view = findViewById(R.id.db_text_view);

        ping_progress = findViewById(R.id.ping_progress);
        db_progress = findViewById(R.id.db_progress);

        updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUi();
            }
        });

        server_ip_addr_text_view = findViewById(R.id.server_ip_addr_text_view);
        update_text_view = findViewById(R.id.update_text_view);
        update_progress = findViewById(R.id.update_progress);
    }

    private WifiInfo wifiInfo;
    private String pingState,dbState;
    private String getSSID(){
        return wifiInfo.getSSID();
    }

    private void updateUi() {

        ping_progress.setVisibility(View.VISIBLE);
        db_progress.setVisibility(View.VISIBLE);
        update_progress.setVisibility(View.VISIBLE);

        ping_text_view.setText("");
        db_text_view.setText("");
        update_text_view.setText("");

        getWifi();
        getPing();

        if (wifiInfo != null) {
            wifi_ssid_text_view.setText(getSSID());
            ip_addr_text_view.setText(getWiFiIp());
        }

        server_ip_addr_text_view.setText(AppConfigurator.getServerIp());
    }





    private void getWifi() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService (Context.WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo ();
    }
    private String getWiFiIp() {
        int ip = wifiInfo.getIpAddress();
        String result = String.format("%d.%d.%d.%d", (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff),
                (ip >> 24 & 0xff));
        return result;
    }

    private void getPing() {

        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                ping_text_view.setText(pingState);
                ping_progress.setVisibility(View.GONE);
                getDbConnection();
                getUpdateServerConnection();
            }
        };

        run0 = new Runnable() {
            @Override
            public void run() {
                pinging();
                handler.sendEmptyMessage(0);
            }
        };

        thread0 = new Thread(run0);
        thread0.start();
    }
    private void pinging() {

        Runtime runtime = Runtime.getRuntime();
        Process proc = null;
        try {
            proc = runtime.exec("ping -c 1 " + AppConfigurator.getServerIp());
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int exit = proc.exitValue();
        if (exit == 0){
            pingState = "Сервер доступен";
        } else {
            pingState = "Сервер не доступен";
        }
    }

    private void getDbConnection(){
        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                db_text_view.setText(dbState);
                db_progress.setVisibility(View.GONE);
            }
        };

        run1 = new Runnable() {
            @Override
            public void run() {
                testConnection();
                handler.sendEmptyMessage(0);
            }
        };

        thread1 = new Thread(run1);
        thread1.start();

    }
    private void testConnection() {
        try {
            MsSqlConnection con = new MsSqlConnection();
            TestConnectionQuery q = new TestConnectionQuery(con.getConnection());
            con.CallQuery(q);
            dbState = "Бд доступна";
        } catch (Exception e) {
            dbState = "Бд не доступна";
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home :
                finish();
                return true;
            default: return true;
        }
    }


    private void getUpdateServerConnection() {
        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                update_text_view.setText( msg.getData().getString("MSG"));
                update_progress.setVisibility(View.GONE);
            }
        };

        run2 = new Runnable() {
            @Override
            public void run() {
                int responceCode = 200;
                String message = "Сервер досупен";
                try {
                    responceCode = testUpdateConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                    message = "Сервер не досупен";
                }

                if (responceCode != 200) {
                    message = "Сервер не досупен";
                }

                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("MSG",message);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        };

        thread2 = new Thread(run2);
        thread2.start();
    }

    private int testUpdateConnection() throws IOException {
        int responceCode;
        String urlLink;
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("http://");
        urlBuilder.append(AppConfigurator.getServerIp());
        urlBuilder.append(":");
        urlBuilder.append(AppConfigurator.getUpdSvrPort());
        urlBuilder.append("/index.php?route=demo/getUpdateForTSD&fn=app-debug");
        urlLink = urlBuilder.toString();

        URL url = new URL(urlLink);
        HttpURLConnection httpConnection;
        httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.setConnectTimeout(5000);
        httpConnection.setRequestMethod("GET");
        httpConnection.setDoOutput(true);
        httpConnection.connect();
        responceCode = httpConnection.getResponseCode();
        httpConnection.disconnect();
        return responceCode;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (thread0 != null) {
            thread0.interrupt();
        }

        if (thread1 != null) {
            thread1.interrupt();
        }

        if (thread2 != null) {
            thread2.interrupt();
        }
    }
}
