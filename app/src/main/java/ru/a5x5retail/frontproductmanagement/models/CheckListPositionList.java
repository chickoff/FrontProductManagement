package ru.a5x5retail.frontproductmanagement.models;

import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.models.CheckingListControlQty;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;

public class CheckListPositionList {


    private List<CheckingListPosition> list;

    public CheckListPositionList(List<CheckingListPosition> list, List<CheckingListControlQty> controlQtyList) {
        this.list = list;
        this.controlQtyList = controlQtyList;
    }

    public List<CheckingListPosition> getList() {
        if (list == null) {
            throw new NullPointerException("Object not initialized!");
        }
        return list;
    }


    private List<CheckingListControlQty> controlQtyList;
    public List<CheckingListControlQty> getControlQtyList() {
        return controlQtyList;
    }


    protected List<CheckingListPosition> getListInternal() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void add(CheckingListPosition position) {
        getListInternal().add(position);
    }

    public void delete(CheckingListPosition position) {
        getListInternal().remove(position);
    }

    public void replace(CheckingListPosition oldPosition,CheckingListPosition newPosition) {

    }
}
