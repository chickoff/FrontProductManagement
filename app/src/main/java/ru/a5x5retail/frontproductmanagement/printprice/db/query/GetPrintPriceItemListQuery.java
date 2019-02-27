package ru.a5x5retail.frontproductmanagement.printprice.db.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.printprice.db.converters.PrintPriceItemConverter;
import ru.a5x5retail.frontproductmanagement.printprice.db.models.PrintPriceItem;
import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IQuery;


public class GetPrintPriceItemListQuery implements IQuery {

    private String headerGUID;
    private List<PrintPriceItem> PPList;
    private final String sql = "{call V_StoreTSD.dbo.[CheckingListGetGoods](?)}";
    CallableStatement stmt = null;

    public GetPrintPriceItemListQuery(String headerGUID) {
        this.headerGUID = headerGUID;
        PPList = new ArrayList<>();
    }

    @Override
    public void Execute(Connection connection) {
        try {
            stmt = connection.prepareCall(sql);
            stmt.setString(1,headerGUID);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            PrintPriceItemConverter converter = new PrintPriceItemConverter();
            while (rs.next()) {
                PrintPriceItem head = new PrintPriceItem();
                converter.Convert(rs,head);
                PPList.add(head);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<PrintPriceItem> getItemList() {
        return PPList;
    }
}