package ru.a5x5retail.frontproductmanagement.db.currentquery;

import java.util.HashMap;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQueryAsync;

public class CurrentQueryList<T extends CallableQueryAsync> {

    private static HashMap<Integer,CallableQueryAsync> activeQueryMap;

    private static HashMap<Integer,CallableQueryAsync> getActiveQueryMap() {
        if (activeQueryMap == null) {
            activeQueryMap = new HashMap<>();
        }
        return activeQueryMap;
    }



    public void addQuery(Integer id, T queryAsync) {
        getActiveQueryMap().put(id,queryAsync);
    }

    public T getQuery(Integer id) {
        return (T) getActiveQueryMap().get(id);
    }
}
