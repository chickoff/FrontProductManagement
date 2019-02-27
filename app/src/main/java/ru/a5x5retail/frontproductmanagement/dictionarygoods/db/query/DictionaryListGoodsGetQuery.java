package ru.a5x5retail.frontproductmanagement.dictionarygoods.db.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.dictionarygoods.db.converters.DictionaryListGoodsConverter;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.db.models.DictionaryListGoods;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.interfaces.IQuery;

public class DictionaryListGoodsGetQuery implements IQuery {

    private final String sql = "{call V_StoreTSD.[dbo].[DictionaryGetGoods](?,?)}";
    CallableStatement stmt = null;
    private List<DictionaryListGoods> dgList;
    String imeiID,search,chekinListGuid;


    public DictionaryListGoodsGetQuery(String imeiID,String search, String chekinListGuid) {
        dgList = new ArrayList<>();
        this.imeiID=imeiID;
        this.search=search;
        this.chekinListGuid=chekinListGuid;
    }

    @Override
    public void Execute(Connection connection) {

        try {
            stmt = connection.prepareCall(sql);
            stmt.setString(1,search);
            stmt.setString(2,chekinListGuid);

            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            DictionaryListGoodsConverter converter = new DictionaryListGoodsConverter();
            while (rs.next()) {
                DictionaryListGoods head = new DictionaryListGoods();
                converter.Convert(rs,head);
                dgList.add(head);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DictionaryListGoods> getItemList()
    {

        return dgList;
    }

}
