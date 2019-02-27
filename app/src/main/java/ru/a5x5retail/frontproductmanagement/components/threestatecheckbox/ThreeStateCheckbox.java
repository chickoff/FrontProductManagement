package ru.a5x5retail.frontproductmanagement.components.threestatecheckbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import ru.a5x5retail.frontproductmanagement.R;

public class ThreeStateCheckbox extends android.support.v7.widget.AppCompatCheckBox {


    public ThreeStateCheckbox(Context context) {
        super(context);
        init(null);
    }

    public ThreeStateCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ThreeStateCheckbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private int CHECKED = 1;
    private int UNCHECKED = 0;
    private int UNKNOWN = -1;

    private static final int[] UNKNOWN_STATE = {R.attr.state_unknownState};

    boolean unknownState;

    private void init(AttributeSet attrs) {

        TypedArray a =  getContext().obtainStyledAttributes(attrs, R.styleable.ThreeStateCheckbox);
        if (a == null) {
            return;
        }
        boolean s = a.getBoolean(R.styleable.ThreeStateCheckbox_state_unknownState,false);
        if (s != true) {
            // do something
        }

        upd();

    }


/*    public int getState(){
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }*/


    private void upd(){

        int btnDrawable = R.drawable.three_state_checkbox_selector;
        setButtonDrawable(btnDrawable);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if (unknownState){
            final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(drawableState, UNKNOWN_STATE);
            return drawableState;
        } else return super.onCreateDrawableState(extraSpace);
    }

    @Override
    public void setButtonDrawable(int resId) {
        super.setButtonDrawable(resId);
    }

    @Override
    public void setButtonDrawable(Drawable buttonDrawable) {
        super.setButtonDrawable(buttonDrawable);
    }

    public boolean getUnknownState() {
        return unknownState;
    }

    public void setUnknownState(boolean unknownState) {
        this.unknownState = unknownState;
        refreshDrawableState();
    }

    public interface OnStateChangeListener {
        void onStateChanged(CompoundButton buttonView, int state);
    }


    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        //upd();
    }
}
