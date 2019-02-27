package ru.a5x5retail.frontproductmanagement.dictionarygoods.document.viewmodel;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.dictionarygoods.base.BaseViewModel;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.db.models.DictionaryListGoods;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.db.query.DictionaryListGoodsGetQuery;

public class DictionaryListGoodsViewModel extends BaseViewModel {

    private List<DictionaryListGoods> dgList;


    @Override
    @Deprecated
    public final void Load() {
        //здесь надо отключить базовую перегрузку метода Load(), чтобы ее случайно не вызвать
    }


    //  @Override
    public void Load(String imeiID,String search,String headerGUID) {
        super.Load();


        MsSqlConnection con = new MsSqlConnection();



            DictionaryListGoodsGetQuery query = new DictionaryListGoodsGetQuery(imeiID,search,headerGUID);
            query.Execute(con.getConnection());


        dgList = query.getItemList();
    }

    public List<DictionaryListGoods> getItemList() {
        return dgList;
    }


}
