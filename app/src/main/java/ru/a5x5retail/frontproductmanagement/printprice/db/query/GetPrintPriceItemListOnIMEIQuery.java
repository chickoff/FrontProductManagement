package ru.a5x5retail.frontproductmanagement.printprice.db.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IQuery;

public class GetPrintPriceItemListOnIMEIQuery implements IQuery {

    private String  imei,headGuid;
   // private List<PrintPriceItem> PPList;
    private final String sql = "{call V_StoreTSD.dbo.PrintPriceGetHeaderGuid(?, ?)}";
    CallableStatement stmt = null;


    public GetPrintPriceItemListOnIMEIQuery( String imei) {

        this.imei = imei;
        this.headGuid = headGuid;
       // PPList = new ArrayList<>();

    }

    @Override
    public void Execute(Connection connection) {

        try {
            stmt = connection.prepareCall(sql);
            stmt.setString(1,imei);
            stmt.registerOutParameter(2, Types.OTHER);
            stmt.execute();
            headGuid = stmt.getString(2);

           // ResultSet rs = stmt.getResultSet();
          //  PrintPriceItemConverter converter = new PrintPriceItemConverter();
           /* while (rs.next()) {
                PrintPriceItem head = new PrintPriceItem();
                converter.Convert(rs,head);
                PPList.add(head);
            }*/

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getHeadGuid() {
        return headGuid;
    }


}
