package ru.a5x5retail.frontproductmanagement.filters.assortmentfilter;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.db.models.DictionaryListGoods;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetDictionaryListGoodsQuery;


@InjectViewState
public class AssortmentListenerPresenter extends BasePresenter<IAssortmentFilterView> {


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Load("");
    }

    public void setList(List<DictionaryListGoods> list) {
        this.list = list;
        getViewState().updateView();
    }

    List<DictionaryListGoods> list;
    GetDictionaryListGoodsQuery query;
    public void Load(String s) {
         if (query != null && query.isRunning()) return;
        query = new GetDictionaryListGoodsQuery(AppConfigurator.getDeviceId(ProdManApp.getAppContext()),s);
        registerAwaitEvents(query);
        query.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
            @Override
            public void onPostExecute() {
                setList(query.getItemList());
            }
        });

        query.ExecuteAsync();
    }
}
