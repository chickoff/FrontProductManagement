package ru.a5x5retail.frontproductmanagement.base;

import ru.a5x5retail.frontproductmanagement.db.query.DataBaseQuery;

public interface IDataBaseQueryEvent {
    void addOnPreExecuteListener(DataBaseQuery.OnPreExecuteListener pre);
    void removeOnPreExecuteListener(DataBaseQuery.OnPreExecuteListener pre);

    void addOnPostExecuteListener(DataBaseQuery.OnPostExecuteListener post);
    void removeOnPostExecuteListener(DataBaseQuery.OnPostExecuteListener post);

    void addOnProgressUpdateListener(DataBaseQuery.OnProgressUpdateListener progress);
    void removeOnProgressUpdateListener(DataBaseQuery.OnProgressUpdateListener progress);

    void addOnCancelledListener(DataBaseQuery.OnCancelledListener canceled);
    void removeOnCancelledListener(DataBaseQuery.OnCancelledListener canceled);


    void addAsyncQueryEventListener(DataBaseQuery.IAsyncQueryEventListener listener);
    void removeAsyncQueryEventListener(DataBaseQuery.IAsyncQueryEventListener listener);

    void addAsyncExceptionEventListener(DataBaseQuery.IAsyncQueryExceptionListener listener);
    void removeAsyncvEventListener(DataBaseQuery.IAsyncQueryExceptionListener listener);

    void addOnSuccessfulListener(DataBaseQuery.OnSuccessfulListener listener);
    void removeOnSuccessfulListener(DataBaseQuery.OnSuccessfulListener listener);
}
