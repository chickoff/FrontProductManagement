package ru.a5x5retail.frontproductmanagement.сheckinglist.interfaces;

import java.sql.ResultSet;

public interface IDbConvertible<T> {
    boolean Convert(ResultSet resultSet, T obj);
}
