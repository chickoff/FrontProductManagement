package ru.a5x5retail.frontproductmanagement.printprice.db.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.printprice.db.converters.GetSKUContextConverter;
import ru.a5x5retail.frontproductmanagement.printprice.db.models.SKUContext;
import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IQuery;


public class PrintPriceItemGetSKUInfo implements IQuery {

    private String barcode;
    private List<SKUContext> skuInfo;
    private final String sql = "{call [V_StoreTSD].[dbo].[GetSKUContext](?)}";
    CallableStatement stmt = null;

    public PrintPriceItemGetSKUInfo(String barcode) {
        this.barcode = barcode;
        skuInfo = new ArrayList<>();
    }

    @Override
    public void Execute(Connection connection) {
        try {
            stmt = connection.prepareCall(sql);
            stmt.setString(1,barcode);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            GetSKUContextConverter converter = new GetSKUContextConverter();
            while (rs.next()) {
                SKUContext head = new SKUContext();
                converter.Convert(rs,head);
                skuInfo.add(head);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public SKUContext getSKUInfoList() {
        if (skuInfo == null || skuInfo.size() == 0){
            return null;
        }
        return skuInfo.get(0);
    }
}