package ru.a5x5retail.frontproductmanagement.newdocumentmaster.invoicemasters;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.BaseMasterActivity;

public class InvoiceMasterActivity extends BaseMasterActivity {

    ImageView alertImageView;

    protected void init(){
        setOkButton((Button) findViewById(R.id.a_master_ok));
        setCancelButton((Button) findViewById(R.id.a_master_cancel));
        alertImageView = findViewById(R.id.alertImageView);
    }

    public void showAlertImage(boolean isShow ){
        if (isShow) {
            alertImageView.setVisibility(View.VISIBLE);
        } else {
            alertImageView.setVisibility(View.INVISIBLE);
        }
    }

    protected void initViewModel(){

    }
}
