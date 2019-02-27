package ru.a5x5retail.frontproductmanagement.filters.groupfilter;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name="group")
public class SkuGroup {

    @Element(name="GUID")
    public String guid;

    @Element(name= "Code")
    public int id;

    @Element(name="NameLong")
    public String nameLong;


    @ElementList(name="group", inline=true, required = false)
    public List<SkuGroup> childList;




    private static List<SkuGroup> checkedSkuGroupsList;



    public static void addCheckedSkuGroupsList(SkuGroup skuGroup) {

        if (checkedSkuGroupsList == null) {checkedSkuGroupsList = new ArrayList<>(); }
        SkuGroup.checkedSkuGroupsList.add(skuGroup);
    }

    public static void removeCheckedSkuGroupsList(SkuGroup skuGroup) {

        if (checkedSkuGroupsList == null) {checkedSkuGroupsList = new ArrayList<>(); }
        SkuGroup.checkedSkuGroupsList.remove(skuGroup);
    }

    public static List<SkuGroup> getCheckedSkuGroupsList() {
        return checkedSkuGroupsList;
    }



    private SkuGroup parent;
    public SkuGroup getParent(){return parent;}
    public void SetParent(SkuGroup parent){
        this.parent = parent;
        if (childList == null) return;
        for (SkuGroup skuGroup : childList) {
            skuGroup.SetParent(this);
        }
    }

    private boolean checked;
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        if (this.checked == checked) return;
        this.checked = checked;
        setCheckDown(checked);
        if (parent != null) parent.childCheckChange(checked);

        if (childList == null) {

            if (mListener!=null) {
                mListener.lastGroupCheckedChanged(this);
            }

           /*if (checked) {SkuGroup.addCheckedSkuGroupsList(this);}
           else {SkuGroup.removeCheckedSkuGroupsList(this);}*/
        }
    }

    private void setCheckDown(boolean checked){
        if (childList != null) {
            for (SkuGroup skuGroup : childList) {
                skuGroup.setChecked(checked);
            }
        }
    }

    private void setProgramChecked(boolean checked) {
        if (this.checked != checked) {
            this.checked = checked;
            if (parent != null) parent.childCheckChange(checked);
        }
    }

    SKU_STATE state = SKU_STATE.NONE, old_state = null;



    private int count_checked_child = 0;


    public void childCheckChange(boolean check){
        if (check) {
            count_checked_child++;
        }
        else {
            count_checked_child--;
        }
        checkCheckedChild();
    }


    protected void checkCheckedChild() {
        int childCount = 0;
        if (childList != null) {
            childCount = childList.size();

            if (count_checked_child == childCount) {
                setProgramChecked(true);
            }

            if (count_checked_child == 0) {
                setProgramChecked(false);
            }

            if (count_checked_child > 0 && count_checked_child < childCount) {

                setProgramChecked(true);
            }
        }
    }



    public SKU_STATE getState(){

        if (!checked) { return SKU_STATE.NONE; }

        int count_all = 0,count_partial = 0, count_none = 0;
        int childCount = 0;
        if (childList != null) {
            childCount = childList.size();
            for (SkuGroup skuGroup : childList) {
                switch (skuGroup.getState()) {
                    case ALL:
                        count_all++;
                        break;
                    case PARTIAL:
                        count_partial++;
                        break;
                    case NONE:
                        count_none++;
                        break;
                }
            }
        }

        if (childCount == count_all) {return SKU_STATE.ALL; }

        if (count_all < childCount) { return SKU_STATE.PARTIAL; }
        return state;
    }

    public void setState(SKU_STATE state) {
        if (this.state == state) return;
        this.old_state = this.state;
        this.state = state;

    }



    public enum SKU_STATE {ALL, PARTIAL, NONE}

    public interface ICheckedChangedListeler {
        void lastGroupCheckedChanged(SkuGroup group);
    }

   private  ICheckedChangedListeler mListener;
    public void setmListener(ICheckedChangedListeler mListener) {
        this.mListener = mListener;
        if (childList != null) {
            for (SkuGroup skuGroup : childList) {
                skuGroup.setmListener(mListener);
            }
        }
    }
}
