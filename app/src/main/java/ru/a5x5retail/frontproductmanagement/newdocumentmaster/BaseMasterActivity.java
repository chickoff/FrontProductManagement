package ru.a5x5retail.frontproductmanagement.newdocumentmaster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import ru.a5x5retail.frontproductmanagement.base.BaseAppCompatActivity;


public abstract class BaseMasterActivity extends BaseAppCompatActivity {

    private Button a_master_cancel;
    private Button a_master_ok;
    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected Button getOkButton(){
        return a_master_ok;
    }

    protected Button getCancelButton(){
        return a_master_cancel;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    protected void setOkButton(Button button){
        a_master_ok = button;
        a_master_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkButtonClick();
            }
        });
    }

    protected void setCancelButton(Button button){
        a_master_cancel = button;
        a_master_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelButtonClick();
            }
        });

    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    protected void OkButtonClick(){

    }

    protected void CancelButtonClick(){

    }




}
