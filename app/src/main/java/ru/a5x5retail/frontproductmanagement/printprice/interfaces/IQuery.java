package ru.a5x5retail.frontproductmanagement.printprice.interfaces;

import java.sql.Connection;

public interface IQuery {
    void Execute(Connection connection);
}
