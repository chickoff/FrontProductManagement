package ru.a5x5retail.frontproductmanagement;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


class RetrieveFeedTask extends AsyncTask<String, Void, Void> {



    @Override
    protected Void doInBackground(String... strings) {
        try {
            rrr();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void rrr() throws IOException {
        URL url = new URL("http://www.check.5x5retail.ru/index.php?route=demo/getUpdateForTSD&fn=app-debug");
        HttpURLConnection c = (HttpURLConnection) url.openConnection();
        c.setRequestMethod("GET");
        c.setDoOutput(true);
        c.connect();

        File file = ProdManApp.getAppContext().getExternalFilesDir("download");
        File outputFile = new File(file, "app.apk");
        FileOutputStream fos = new FileOutputStream(outputFile);
        InputStream is = c.getInputStream();

        byte[] buffer = new byte[1024];
        int len1 = 0;
        while ((len1 = is.read(buffer)) != -1) {
            fos.write(buffer, 0, len1);
        }
        fos.close();
        is.close();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(outputFile),
                "application/vnd.android.package-archive");
        ProdManApp.getAppContext().startActivity(intent);
    }
}


