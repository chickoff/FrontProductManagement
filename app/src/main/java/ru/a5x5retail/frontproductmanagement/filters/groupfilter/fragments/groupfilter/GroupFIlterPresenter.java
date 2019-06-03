package ru.a5x5retail.frontproductmanagement.filters.groupfilter.fragments.groupfilter;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.base.BasePresenter;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.DataBaseQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetCodeBySubgroupQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetSkuAllGroupsXmlQuery;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.SkuGroup;


@InjectViewState
public class GroupFIlterPresenter extends BasePresenter<IGroupFilterView> {
    private List<SkuGroup> checkedGroupList;

    public GroupFIlterPresenter() {
        checkedGroupList = new ArrayList<>();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getFullClassificator();
    }

    public void getFullClassificator()  {
        final GetSkuAllGroupsXmlQuery q = new GetSkuAllGroupsXmlQuery();

        registerAwaitEvents(q);

        q.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                inner_setCurrentGroup(q.getRootGroup());
            }
        });
        q.ExecuteAsync();
    }

    public void getCode()  {
        if (checkedGroupList.size() == 0) {
            return ;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<root>");
        for (SkuGroup skuGroup : checkedGroupList) {
            sb.append("<gg>").append( skuGroup.guid).append("</gg>");
        }
        sb.append("</root>");
        String s = sb.toString();
        final GetCodeBySubgroupQuery q = new GetCodeBySubgroupQuery(s);

        registerAwaitEvents(q);

        q.addOnSuccessfulListener(new DataBaseQuery.OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                getViewState().setResultSkuList(q.getList());
            }
        });

        q.ExecuteAsync();
    }



    public boolean isEmptyChecked(){
        return checkedGroupList == null || checkedGroupList.size() == 0;
    }


    /******************************************************************************************************/

    private SkuGroup currentGroup;
    public SkuGroup getCurrentGroup() {
        return currentGroup;
    }

    private void inner_setCurrentGroup(SkuGroup currentGroup) {
        this.currentGroup = currentGroup;
        this.currentGroup.setmListener(new SkuGroup.ICheckedChangedListeler() {
            @Override
            public void lastGroupCheckedChanged(SkuGroup group) {
                if (group.isChecked()) {
                    checkedGroupList.add(group);
                } else {
                    checkedGroupList.remove(group);
                }
            }
        });
        getViewState().updateUi();
    }

    public void setCurrentGroup(SkuGroup currentGroup) {
        this.currentGroup = currentGroup;
    }

    /******************************************************************************************************/



}
