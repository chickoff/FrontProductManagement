package ru.a5x5retail.frontproductmanagement.db.query.read.checkinglisthead;

import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;

public abstract class GetCheckListHeadParentQuery extends CallableQAsync {

    protected List<CheckingListHead> headList;

    public List<CheckingListHead> getList() {
        return headList;
    }

}
