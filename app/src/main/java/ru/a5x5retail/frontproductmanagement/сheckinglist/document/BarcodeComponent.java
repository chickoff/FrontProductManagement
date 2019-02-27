package ru.a5x5retail.frontproductmanagement.сheckinglist.document;

import java.math.BigDecimal;

public class BarcodeComponent {

    private String measure;
    private int code;
    private String qty;
    private BigDecimal qtyV;


    public  BarcodeComponent(String barcode )
    {
        this.measure=barcode.substring(0,1);
        this.code=Integer.parseInt(barcode.substring(2,7));
        this.qty=barcode.substring(7,12);
        this.qtyV=BigDecimal.valueOf(Double.parseDouble(barcode.substring(7,9)+"."+barcode.substring(9,12)));
    }

    public String GetMeasure()
    {
        return measure;
    }
    public boolean GetMeasureF()
    {
        return Integer.parseInt(measure)==2;
    }

    public int GetCode()
    {
        return code;
    }

    public String GetQty()
    {
        return qty;
    }
    public BigDecimal GetQtyV()
    {
        return qtyV;
    }



}
