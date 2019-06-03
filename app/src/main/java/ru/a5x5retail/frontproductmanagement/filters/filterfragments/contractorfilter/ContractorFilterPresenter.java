package ru.a5x5retail.frontproductmanagement.filters.filterfragments.contractorfilter;

import com.arellomobile.mvp.InjectViewState;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.DataBaseQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetAllContractorsQuery;


@InjectViewState
public class ContractorFilterPresenter extends BasePresenter<IContractorFilterView> {

    private List<ContractorInfo> contractorList;

    public ContractorFilterPresenter() {
        contractorList = new ArrayList<>();
    }


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Load();
    }

    public void Load() {
        final GetAllContractorsQuery q = new GetAllContractorsQuery();
        registerAwaitEvents(q);
        q.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                setContractorList(q.getList());
            }
        });
        q.ExecuteAsync();
    }


    protected void setContractorList(List<ContractorInfo> contractorList) {
        this.contractorList = contractorList;
        getViewState().updateUi();
    }

    public List<ContractorInfo> getContractorList() {
        return contractorList;
    }

}
