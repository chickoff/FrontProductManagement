package ru.a5x5retail.frontproductmanagement.db.query;

import android.os.AsyncTask;

import java.sql.SQLException;

public abstract class CallableQueryAsync<T,Params, Progress, Result> extends CallableQuery<T> {

    private AsyncQueryListener<Params, Progress, Result> queryListener;

    public CallableQueryAsync() throws SQLException, ClassNotFoundException {
        super();
    }

    public void ExecuteAsync() {
        DbTask task = new DbTask();
        task.execute();
    }

    public AsyncQueryListener getQueryListener() {
        return queryListener;
    }

    public void setQueryListener(AsyncQueryListener queryListener) {
        this.queryListener = queryListener;
    }

    public interface AsyncQueryListener<Params, Progress, Result> {
        void onPreExecute();
        void onPostExecute(Result result);
        void onProgressUpdate(Progress... values);
        void onCancelled(Result result);
        void onCancelled();
    }

    private class DbTask extends AsyncTask<Params, Progress, Result> {


        @Override
        protected Result doInBackground(Params... params) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                CallableQueryAsync.this.Execute();
            } catch (Exception e) {
                CallableQueryAsync.this.setResultSetEnable(false);
                CallableQueryAsync.this.setException(e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (queryListener != null) {
                queryListener.onPreExecute();
            }
        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);

            if (queryListener != null) {
                queryListener.onPostExecute(result);
            }
        }

        @Override
        protected void onProgressUpdate(Progress... values) {
            super.onProgressUpdate(values);

            if (queryListener != null) {
                queryListener.onProgressUpdate(values);
            }
        }

        @Override
        protected void onCancelled(Result result) {
            super.onCancelled(result);

            if (queryListener != null) {
                queryListener.onCancelled(result);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            if (queryListener != null) {
                queryListener.onCancelled();
            }
        }
    }
}
