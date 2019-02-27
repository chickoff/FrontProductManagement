package ru.a5x5retail.frontproductmanagement.dictionarygoods.interfaces;

import java.sql.Connection;

public interface IQuery {
    void Execute(Connection connection);
}
