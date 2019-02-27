package ru.a5x5retail.frontproductmanagement.interfaces;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public interface IQuery {
    void Execute() throws SQLException;
}
