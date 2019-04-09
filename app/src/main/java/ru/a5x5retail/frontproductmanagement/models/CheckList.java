package ru.a5x5retail.frontproductmanagement.models;

import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListMark;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListPosition;

public class CheckList {

    private CheckingListHead head;


   public CheckingListHead getHead() {
        if (head == null) {
            throw new NullPointerException("Object not initialized!");
        }
        return head;
    }

    public void setHead(CheckingListHead head) {
        this.head = head;
    }


    private CheckListPositionList checkListPositionList;

    public CheckListPositionList getCheckListPositionList() {
        return checkListPositionList;
    }

    public void setCheckListPositionList(CheckListPositionList checkListPositionList) {
        this.checkListPositionList = checkListPositionList;
    }


    private CheckListMarkList checkListMarkList;

    public CheckListMarkList getCheckListMarkList() {
        return checkListMarkList;
    }

    public void setCheckListMarkList(CheckListMarkList checkListMarkList) {
        this.checkListMarkList = checkListMarkList;
    }



     /*private List<CheckingListPosition> positionList;

    public List<CheckingListPosition> getPositionList() {
        if (positionList == null) {
            positionList = new ArrayList<>();
        }
        return positionList;
    }

    public void setPositionList(List<CheckingListPosition> positionList) {
        this.positionList = positionList;
    }

    private  List<CheckingListMark> markList;

    public List<CheckingListMark> getMarkList() {
        if (markList == null) {
            markList = new ArrayList<>();
        }
        return markList;
    }

    public void setMarkList(List<CheckingListMark> markList) {
        this.markList = markList;
    }*/
}
