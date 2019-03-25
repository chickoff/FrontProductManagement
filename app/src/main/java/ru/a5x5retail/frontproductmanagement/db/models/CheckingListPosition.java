package ru.a5x5retail.frontproductmanagement.db.models;

import java.math.BigDecimal;
import java.sql.Date;

public class CheckingListPosition {
    public String guid;
    public String checkingListIncHeadGuid;
    public int code;
    public BigDecimal qtyUser;
    public int orderBy;
    public BigDecimal qty;
    public BigDecimal incomeGoodsQty;
    public BigDecimal price;
    public BigDecimal vat;
    public Date manufacturingDate;
    public String nameLong;
    public int measureUnitIdd;
    public Date manufacturingDateFl;
    public int validEror;
}
