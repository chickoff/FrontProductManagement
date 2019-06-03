package ru.a5x5retail.frontproductmanagement.db.models;

import java.math.BigDecimal;
import java.util.UUID;

public class CheckingListGoods {

    public UUID  Guid;
    public UUID CheckingListHeadGuid;
    public Integer Code;
    public String NameLong;
    public Integer MeasureUnitIDD;
    public Boolean Check;
    public BigDecimal Qty;
    public Integer OrderBy;
}


