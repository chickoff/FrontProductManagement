package ru.a5x5retail.frontproductmanagement.db.query.read;


import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.converters.DictionaryListGoodsConverter;
import ru.a5x5retail.frontproductmanagement.db.models.DictionaryListGoods;


public class GetDictionaryListGoodsQuery extends CallableQAsync {


    private List<DictionaryListGoods> dgList;
    String imeiID,search,chekinListGuid;


    public GetDictionaryListGoodsQuery(String imeiID, String search) {
        dgList = new ArrayList<>();
        this.imeiID=imeiID;
        this.search=search;
        this.chekinListGuid = null;
    }


    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.[dbo].[DictionaryGetGoods](?,?)}");
    }

    @Override
    protected void SetQueryParams() {
        try {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++, search);
            getStmt().setString(parameterIndex++, null);
        } catch (Exception e) {
            e.printStackTrace();
            setException(e);
        }
    }


/*    @Override
    protected void Execute() {
        super.Execute();
        try {
            getStmt().execute();
            ResultSet rs = getStmt().getResultSet();
            DictionaryListGoodsConverter converter = new DictionaryListGoodsConverter();
            while (rs.next()) {
                DictionaryListGoods head = new DictionaryListGoods();
                converter.Convert(rs,head);
                dgList.add(head);
            }

        } catch (Exception e) {
            e.printStackTrace();
            setException(e);
        }
    }*/

    @Override
    protected void parseResultSet() throws SQLException {
            DictionaryListGoodsConverter converter = new DictionaryListGoodsConverter();
            while (getResultSet().next()) {
                DictionaryListGoods head = new DictionaryListGoods();
                converter.Convert(getResultSet(),head);
                dgList.add(head);
            }
    }

    public List<DictionaryListGoods> getItemList()
    {
        return dgList;
    }

}
