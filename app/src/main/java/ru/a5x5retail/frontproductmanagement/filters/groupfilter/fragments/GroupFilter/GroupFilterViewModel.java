package ru.a5x5retail.frontproductmanagement.filters.groupfilter.fragments.GroupFilter;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.base.TypedViewModel;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetCodeBySubgroupQuery;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetSkuAllGroupsXmlQuery;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.SkuGroup;

public class GroupFilterViewModel extends TypedViewModel {



    private List<SkuGroup> checkedGroupList;
    private SkuGroup currentGroup;

    public GroupFilterViewModel() {
        checkedGroupList = new ArrayList<>();
    }

    @Override
    public void Load() throws SQLException, ClassNotFoundException {
        super.Load();
        MsSqlConnection con = new MsSqlConnection();
        GetSkuAllGroupsXmlQuery q = new GetSkuAllGroupsXmlQuery(con.getConnection());
        q.Execute();
        currentGroup = q.getRootGroup();

        currentGroup.setmListener(new SkuGroup.ICheckedChangedListeler() {
            @Override
            public void lastGroupCheckedChanged(SkuGroup group) {
                if (group.isChecked()) {
                    checkedGroupList.add(group);
                } else {
                    checkedGroupList.remove(group);
                }
            }
        });
    }

    public List<CodeInfo> getCode() throws SQLException, ClassNotFoundException {

        if (checkedGroupList.size() == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<root>");
        for (SkuGroup skuGroup : checkedGroupList) {
            sb.append("<gg>").append( skuGroup.guid).append("</gg>");
        }
        sb.append("</root>");
        String s = sb.toString();
        MsSqlConnection con = new MsSqlConnection();
        GetCodeBySubgroupQuery q = new GetCodeBySubgroupQuery(con.getConnection(),s);
        q.Execute();
        List<CodeInfo> l = q.getList();
        return l;
    }

    public SkuGroup getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(SkuGroup currentGroup) {
        this.currentGroup = currentGroup;
    }

    public boolean isEmptyChecked(){
        return checkedGroupList == null || checkedGroupList.size() == 0;
    }
}
