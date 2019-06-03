package ru.a5x5retail.frontproductmanagement.filters.groupfilter.fragments.contractorfilter;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorInfo;
import ru.a5x5retail.frontproductmanagement.interfaces.IBaseMvpView;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IContractorFilterView extends IBaseMvpView {
    void setCodeList(List<CodeInfo> codeInfoList);
    void setContractorList(List<ContractorInfo> contractorList);
}
