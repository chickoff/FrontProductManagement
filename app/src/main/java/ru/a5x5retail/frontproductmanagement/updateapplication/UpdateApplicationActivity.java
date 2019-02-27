package ru.a5x5retail.frontproductmanagement.updateapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class UpdateApplicationActivity extends AppCompatActivity {

    TextView a_update_application_textView1;
    Button a_update_app_button_1;
    ProgressBar a_update_app_progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_application);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }

    private void init() {
        a_update_application_textView1 = findViewById(R.id.a_update_application_textView1);
        a_update_app_button_1  = findViewById(R.id.a_update_app_button_1);
        a_update_app_progress = findViewById(R.id.a_update_app_progress);
        a_update_app_button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadAsycTask task = new DownloadAsycTask();
                task.execute();
            }
        });
    }


    private class DownloadAsycTask extends AsyncTask<String, String, Void> {

        private String urlLink;
        private boolean isSuccessfull;
        private String textError;


        @Override
        protected Void doInBackground(String... strings) {
            publishProgress("Начинаем");
            try {
                publishProgress("Подключаемся к удаленному серверу");
                createHttpConnection(urlLink);
                publishProgress("Скачиваем файл...");
                getInputStream();
                getFile();
                publishProgress("Запускаем установку");
                startInstall();
                isSuccessfull = true;
            } catch (ConnectException e) {
                e.printStackTrace();
                isSuccessfull = false;
                textError = "Ошибка соединения с сервером.";
            } catch (IOException e) {
                e.printStackTrace();
                isSuccessfull = false;
                textError = "Ошибка обновления. Проверьте настройки.";
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            a_update_app_progress.setVisibility(View.VISIBLE);
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append("http://");
            urlBuilder.append(AppConfigurator.getServerIp());
            urlBuilder.append(":");
            urlBuilder.append(AppConfigurator.getUpdSvrPort());
            urlBuilder.append("/index.php?route=demo/getUpdateForTSD&fn=app-debug");
            urlLink = urlBuilder.toString();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            a_update_app_progress.setVisibility(View.INVISIBLE);

            if (isSuccessfull) {
                a_update_application_textView1.setText("");
            } else {
                a_update_application_textView1.setText(textError);
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String v = values[0];
            a_update_application_textView1.setText(v);
        }
        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        private HttpURLConnection httpConnection;
        private InputStream inputStream;
        private FileOutputStream fileOutputStream;
        private File outputFile;

        private void createHttpConnection(String strUrl) throws IOException {
            URL url = new URL(strUrl);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setDoOutput(true);
            httpConnection.connect();
        }

        private void getInputStream() throws IOException {
            inputStream = httpConnection.getInputStream();

        }

        private void getFile() throws IOException {
            File file = ProdManApp.getAppContext().getExternalFilesDir("download");
            outputFile = new File(file, "app.apk");
            fileOutputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len1);
            }
            fileOutputStream.close();
            fileOutputStream.close();
        }

        private void startInstall() {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(outputFile),
                    "application/vnd.android.package-archive");
            ProdManApp.getAppContext().startActivity(intent);
        }
    }
}
