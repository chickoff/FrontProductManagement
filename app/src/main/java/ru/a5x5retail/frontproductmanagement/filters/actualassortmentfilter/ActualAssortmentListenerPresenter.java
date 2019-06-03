package ru.a5x5retail.frontproductmanagement.filters.actualassortmentfilter;

import com.arellomobile.mvp.InjectViewState;

import java.util.Date;
import java.util.List;


import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.db.models.DivisionInfo;
import ru.a5x5retail.frontproductmanagement.db.query.DataBaseQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetActualAssortmentQuery;
import ru.a5x5retail.frontproductmanagement.db_local.DatabaseInit;
import ru.a5x5retail.frontproductmanagement.db_local.ProjectMap;
import ru.a5x5retail.frontproductmanagement.db_local.Settings;
import ru.a5x5retail.frontproductmanagement.db_local.loSkuContext;
import ru.a5x5retail.frontproductmanagement.db_local.repository.SettingsRepository;
import ru.a5x5retail.frontproductmanagement.db_local.repository.SkuContextRepository;

@InjectViewState
public class ActualAssortmentListenerPresenter extends BasePresenter<IActualAssortmentFilterView> {


    SettingsRepository repository;
    SkuContextRepository repository1;
    public ActualAssortmentListenerPresenter() {
        repository = new SettingsRepository();
        repository1 = new SkuContextRepository();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().updateView();
        //Load(new Date(2019,5,1));
    }

    public List<loSkuContext> getAssortmentList(String search) {

        Settings assdiv = repository.getValue("AssortmentDivisionGuid");
        DivisionInfo info = ProjectMap.getMainInfo();
        if(assdiv == null || !info.guid.equals(assdiv.getValue())) {
            Load(new Date(2019,5,1));
        }

        return  repository1.find(search);
    }

    GetActualAssortmentQuery q;

    public void Load(Date date) {
        q = new GetActualAssortmentQuery(date);
        registerAwaitEvents(q);
        q.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                List<loSkuContext> skuContextList = q.getList();
                DatabaseInit.getDaoSession().getLoSkuContextDao().deleteAll();
                DatabaseInit.getDaoSession().getLoSkuContextDao().insertInTx(skuContextList);
                repository.setValue("AssortmentDivisionGuid",ProjectMap.getMainInfo().guid);
                getViewState().updateView();
            }
        });
        q.ExecuteAsync();
    }
}
