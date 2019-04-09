package ru.a5x5retail.frontproductmanagement.db.models;

import java.math.BigDecimal;
import java.sql.Date;

public class CheckingListPosition
        implements Comparable<CheckingListPosition>
{
    public String guid;
    public String checkingListIncHeadGuid;
    public int code;
    public BigDecimal qtyUser;
    public int orderBy;
    public BigDecimal incomeGoodsQty;
    public BigDecimal price;
    public int vat;
    public Date manufacturingDate;
    public String nameLong;
    public int measureUnitIdd;
    public int manufacturingDateFl;
    public int validEror;

    public int compareQty() {
        return incomeGoodsQty.compareTo(qtyUser);
    }

    @Override
    public int compareTo(CheckingListPosition o) {
        return 0;
    }
}
