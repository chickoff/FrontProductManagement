package ru.a5x5retail.frontproductmanagement.filters.groupfilter.fragments.groupfilter;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.interfaces.IBaseMvpView;

public interface IGroupFilterView extends IBaseMvpView {
    void setResultSkuList(List<CodeInfo> skuList);
    void updateUi();
}
