package ru.a5x5retail.frontproductmanagement.сheckinglist.document.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.сheckinglist.QueryString;
import ru.a5x5retail.frontproductmanagement.сheckinglist.base.BaseViewModel;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.converters.CheckingListGoodsConverter;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.factory.CreationObjectFactory;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.models.CheckingListGoods;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.mssql.MsDb;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.query.CheckingListGoodsAddQuery;


public class DocumentItemsViewModel extends BaseViewModel {

    private List<CheckingListGoods> goodsList;

    @Override
    @Deprecated
    public final void Load() {
        //здесь надо отключить базовую перегрузку метода Load(), чтобы ее случайно не вызвать
    }

    // @Override
    public void Load(UUID checkingListHeadGUID) {

        super.Load();

        //UUID checkingListHeadGUID

        String[] v = AppConfigurator.getConnectionString();
        MsDb<CheckingListGoods> db = new MsDb<>(v[0],"V_StoreTSD",v[1],v[2]);
        goodsList = db.ExequteQuery(
                QueryString.getCheckingListGoods(checkingListHeadGUID),
                new CreationObjectFactory<>(CheckingListGoods.class),
                new CheckingListGoodsConverter());
    }

    public List<CheckingListGoods> getGoodsList() {
        return goodsList;
    }

    public void addCodes(UUID checkingListHeadGUID,ArrayList<CodeInfo> codeInfoList) {
        MsSqlConnection con = new MsSqlConnection();

        StringBuilder sb = new StringBuilder();
        sb.append("<root>");
        for (CodeInfo codeInfo : codeInfoList) {
            sb.append("<code>").append(codeInfo.code).append("</code>");
        }
        sb.append("</root>");
        CheckingListGoodsAddQuery query = new CheckingListGoodsAddQuery(checkingListHeadGUID.toString(),
                sb.toString());
        query.Execute(con.getConnection());
    }


}