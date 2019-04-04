package ru.a5x5retail.frontproductmanagement;

import java.math.BigDecimal;

import ru.a5x5retail.frontproductmanagement.db.models.SKUContext;

public class BInfo {
    public String barcode;

    public BInfo(String barcode) {
        this.barcode = barcode;
    }

    public boolean isLocal() {

        if (barcode.length() < 13) {
            return false;
        }

        return Integer.parseInt(barcode.substring(0,1))  == 2;
    }

    public boolean isWeightAvailable() {
        if (barcode.length() < 13) {
            return false;
        }
        return Integer.parseInt(barcode.substring(0,2))  == 28;
    }

    public int getLocalSku() {
        if (!isLocal()) {
            return -1;
        }

        return Integer.parseInt(barcode.substring(2,7));
    }

    public BigDecimal getLocalWeight() {
        if (!isLocal()) {
            return null;
        }

        return BigDecimal.valueOf(Double.parseDouble(barcode.substring(7,9)+"."+barcode.substring(9,12)));

    }

    private SKUContext skuContext;
    public SKUContext getSkuContext() {
        return skuContext;
    }

    public void setSkuContext(SKUContext skuContext) {
        this.skuContext = skuContext;
    }
}
