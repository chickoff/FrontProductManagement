package ru.a5x5retail.frontproductmanagement.printprice.document.viewmodel;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.printprice.base.BaseViewModel;
import ru.a5x5retail.frontproductmanagement.printprice.db.models.PrintPriceItem;
import ru.a5x5retail.frontproductmanagement.printprice.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.printprice.db.query.GetPrintPriceItemListOnIMEIQuery;
import ru.a5x5retail.frontproductmanagement.printprice.db.query.GetPrintPriceItemListQuery;
import ru.a5x5retail.frontproductmanagement.printprice.db.query.PrintPriceItemCheckedAllQuery;
import ru.a5x5retail.frontproductmanagement.printprice.db.query.PrintPriceItemDeleteQuery;


public class PrintPriceItemsViewModel extends BaseViewModel {
    private List<PrintPriceItem> ppitemList;
    private Constants.TypeOfDocument typeOfDocument;
    private  String headerGuid;


    @Override
    @Deprecated
    public final void Load() {
        //здесь надо отключить базовую перегрузку метода Load(), чтобы ее случайно не вызвать
    }


  //  @Override
    public void Load(String imeiID,String headGuid ) {
        super.Load();
        if(headerGuid== null) {
            headerGuid = headGuid;
        }

        MsSqlConnection con = new MsSqlConnection();

        if(headerGuid== null){

        GetPrintPriceItemListOnIMEIQuery query = new GetPrintPriceItemListOnIMEIQuery(imeiID);
        query.Execute(con.getConnection());
            headerGuid=query.getHeadGuid();


//прочищаем
            PrintPriceItemCheckedAllQuery queryCA = new PrintPriceItemCheckedAllQuery(headerGuid);
            con.CallQuery(queryCA);

            PrintPriceItemDeleteQuery queryD = new PrintPriceItemDeleteQuery(headerGuid);
            con.CallQuery(queryD);

        }

        //MsSqlConnection con = new MsSqlConnection();



        //query.getHeadGuid()
        GetPrintPriceItemListQuery query1 = new GetPrintPriceItemListQuery(headerGuid, AppConfigurator.getDeviceId(ProdManApp.getAppContext()));
        query1.Execute(con.getConnection());

       // con.CallQuery();
        ppitemList = query1.getItemList();
    }

    public String getHeadGuid() {
        return headerGuid;
    }


    public List<PrintPriceItem> getItemList() {
        return ppitemList;
    }
    public Constants.TypeOfDocument getTypeOfDocument() {
        return typeOfDocument;
    }
    public void setTypeOfDocument(Constants.TypeOfDocument typeOfDocument) {
        this.typeOfDocument = typeOfDocument;
    }
}