package ru.a5x5retail.frontproductmanagement.db.query.read;


import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.converters.CheckingListGoodsConverter;
import ru.a5x5retail.frontproductmanagement.db.converters.DictionaryListGoodsConverter;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListGoods;
import ru.a5x5retail.frontproductmanagement.db.models.DictionaryListGoods;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;


public class GetCheckingListGoodsQuery extends CallableQAsync {


    private List<CheckingListGoods> dgList;
    String chekinListGuid;


    public GetCheckingListGoodsQuery(String chekinListGuid) {
        dgList = new ArrayList<>();
        this.chekinListGuid = chekinListGuid;
    }


    @Override
    protected void SetQuery() {
        setSqlString("? = call V_StoreTSD.dbo.CheckingListGetGoods(?)}");
    }

    @Override
    protected void SetQueryParams() throws SQLException {
            parameterIndex = 1;
            getStmt().registerOutParameter(parameterIndex++, Types.INTEGER);
            getStmt().setString(parameterIndex++, chekinListGuid);
    }

    @Override
    protected void parseResultSet() throws SQLException {
            CheckingListGoodsConverter converter = new CheckingListGoodsConverter();
            while (getResultSet().next()) {
                CheckingListGoods head = new CheckingListGoods();
                converter.Convert(getResultSet(),head);
                dgList.add(head);
            }
    }

    public List<CheckingListGoods> getItemList()
    {
        return dgList;
    }

}
