package ru.a5x5retail.frontproductmanagement;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.create.SendExceptionFileQuery;

public class SendExceptionFiles {

    private boolean isWork;

    public SendExceptionFiles() {
        isWork = false;
    }

    public void SendAsync(){
        T t = new T();
        t.execute();
    }

    public void Send(){
        try {
            SendAllFiles();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void SendAllFiles () throws IOException, SQLException, ClassNotFoundException {
        File dir = ProdManApp.getAppContext().getExternalFilesDir("events");

        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return;
        }

        for (File file : dir.listFiles()) {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            br.close();
            sendToDb(sb.toString());
            file.delete();
        }
    }

    private void sendToDb(String file) throws SQLException, ClassNotFoundException {
        MsSqlConnection con = new MsSqlConnection();
        SendExceptionFileQuery query = new SendExceptionFileQuery(con.getConnection(), AppConfigurator.getDeviceId(ProdManApp.getAppContext()),file);
        query.Execute();
    }

    public boolean isWork() {
        return isWork;
    }

    public void setWork(boolean work) {
        isWork = work;
    }


    public class T extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            SendExceptionFiles.this.setWork(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            SendExceptionFiles.this.setWork(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            SendExceptionFiles.this.Send();

            return null;
        }
    }

}
