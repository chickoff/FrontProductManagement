package ru.a5x5retail.frontproductmanagement.сheckinglist;

import java.util.UUID;

public class QueryString {

    public final static String getCheckingListGetHead(Integer typeDocID){
        return "EXECUTE V_StoreTSD.dbo.CheckingListGetHead " + typeDocID.toString();
    }


    public final static String getCheckingListGoods(UUID checkingListHeadGUID){
        return "EXECUTE V_StoreTSD.dbo.CheckingListGetGoods '" + checkingListHeadGUID+"'";
    }

    public final static String getSKUContext(String barcode){
        return "EXECUTE V_StoreTSD.dbo.CheckingListGetGoods '" + barcode+"'";
    }



}
