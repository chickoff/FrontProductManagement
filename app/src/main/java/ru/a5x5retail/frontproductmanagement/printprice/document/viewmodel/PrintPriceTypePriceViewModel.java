package ru.a5x5retail.frontproductmanagement.printprice.document.viewmodel;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.printprice.base.BaseViewModel;
import ru.a5x5retail.frontproductmanagement.printprice.db.models.PrintPriceTypePrice;
import ru.a5x5retail.frontproductmanagement.printprice.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.printprice.db.query.GetPrintPriceTypePriceQuery;

//import ru.a5x5retail.frontproductmanagement.printprice.db.models.PrintPriceItem;
//import ru.a5x5retail.frontproductmanagement.printprice.db.query.GetPrintPriceItemListOnIMEIQuery;
//import ru.a5x5retail.frontproductmanagement.printprice.db.query.GetPrintPriceItemListQuery;
//import ru.a5x5retail.frontproductmanagement.printprice.db.query.PrintPriceItemCheckedAllQuery;
//import ru.a5x5retail.frontproductmanagement.printprice.db.query.PrintPriceItemDeleteQuery;


public class PrintPriceTypePriceViewModel extends BaseViewModel {

    private List<PrintPriceTypePrice> ppitemList;
    private PrintPriceTypePrice selectedPPitem;
   // private Constants.TypeOfDocument typeOfDocument;
  //  private  String headerGuid;


    @Override
    @Deprecated
    public final void Load() {
        //здесь надо отключить базовую перегрузку метода Load(), чтобы ее случайно не вызвать
    }


  //  @Override
    public void Load(String imeiID) {
        super.Load();


        MsSqlConnection con = new MsSqlConnection();

        GetPrintPriceTypePriceQuery query1 = new GetPrintPriceTypePriceQuery();
        query1.Execute(con.getConnection());

        ppitemList = query1.getItemList();
    }






    ///////
    public PrintPriceTypePrice getSelectedPPitem() {
        return selectedPPitem;
    }

    public void setSelectedPPitem(PrintPriceTypePrice selectedPPitemList) {
        this.selectedPPitem = selectedPPitemList;
    }

/////////
    public List<PrintPriceTypePrice> getPpitemList() {
        return ppitemList;
    }

    public void setPpitemList(List<PrintPriceTypePrice> ppitemList) {
        this.ppitemList = ppitemList;
    }



   /* public Constants.TypeOfDocument getTypeOfDocument() {
        return typeOfDocument;
    }
    public void setTypeOfDocument(Constants.TypeOfDocument typeOfDocument) {
        this.typeOfDocument = typeOfDocument;
    }*/
}