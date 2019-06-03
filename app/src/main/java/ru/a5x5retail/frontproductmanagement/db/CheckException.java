package ru.a5x5retail.frontproductmanagement.db;


import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.db.query.BaseQuery;

public class CheckException {
    BaseQuery query;
    public CheckException(BaseQuery query) {
        this.query = query;
    }

    public void check() throws Exception {
        if (query.getException() == null) {
            return;
        }
        Exception exception = query.getException();

        if (exception instanceof SQLException) {
            SQLException ex = (SQLException) exception;
            throw ex;
        }

        if (exception instanceof ClassNotFoundException) {
            ClassNotFoundException ex = (ClassNotFoundException) exception;
            throw ex;
        }

        throw exception;
    }
}
