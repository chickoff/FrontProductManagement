package ru.a5x5retail.frontproductmanagement.models;

import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.models.CheckingListMark;

public class CheckListMarkList {

    private List<CheckingListMark> list;

    public CheckListMarkList(List<CheckingListMark> list) {
        this.list = list;
    }

    public List<CheckingListMark> getList() {
        if (list == null) {
            throw new NullPointerException("Object not initialized!");
        }
        return list;
    }

    protected List<CheckingListMark> getListInternal() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void add(CheckingListMark mark) {
        getListInternal().add(mark);
    }

    public void delete(CheckingListMark mark) {
        getListInternal().remove(mark);
    }

    public void replace(CheckingListMark oldMark,CheckingListMark newMark) {

    }
}
