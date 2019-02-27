package ru.a5x5retail.frontproductmanagement.printprice.db.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.printprice.db.converters.PrintPriceTypePriceConverter;
import ru.a5x5retail.frontproductmanagement.printprice.db.models.PrintPriceTypePrice;
import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IQuery;

//import ru.a5x5retail.frontproductmanagement.printprice.db.converters.PrintPriceItemConverter;
//import ru.a5x5retail.frontproductmanagement.printprice.db.models.PrintPriceItem;


public class GetPrintPriceTypePriceQuery implements IQuery {


    private List<PrintPriceTypePrice> PPListpt;
    private final String sql = "{call V_StoreTSD.dbo.[PrintPriceGetTypePrice]}";
    CallableStatement stmt = null;

    public GetPrintPriceTypePriceQuery() {

        PPListpt = new ArrayList<>();
    }

    @Override
    public void Execute(Connection connection) {
        try {
            stmt = connection.prepareCall(sql);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            PrintPriceTypePriceConverter converter = new PrintPriceTypePriceConverter();
            while (rs.next()) {
                PrintPriceTypePrice head = new PrintPriceTypePrice();
                converter.Convert(rs,head);
                PPListpt.add(head);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<PrintPriceTypePrice> getItemList() {
        return PPListpt;
    }
}