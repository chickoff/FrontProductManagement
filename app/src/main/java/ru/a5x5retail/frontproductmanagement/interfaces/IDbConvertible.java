package ru.a5x5retail.frontproductmanagement.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDbConvertible<T> {
    boolean Convert(ResultSet resultSet,T obj) throws SQLException;
}
