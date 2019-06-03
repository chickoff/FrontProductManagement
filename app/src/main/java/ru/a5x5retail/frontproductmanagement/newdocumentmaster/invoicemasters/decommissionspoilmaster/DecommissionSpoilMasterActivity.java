package ru.a5x5retail.frontproductmanagement.newdocumentmaster.invoicemasters.decommissionspoilmaster;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.databinding.ActivityDecommissionSpoilMasterBinding;
import ru.a5x5retail.frontproductmanagement.db.models.IncomeInvoiceHead;
import ru.a5x5retail.frontproductmanagement.db.models.OutgoInvoiceHead;
import ru.a5x5retail.frontproductmanagement.interfaces.IRecyclerViewItemClick;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.BaseMasterActivity;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.setpsfragmentdialog.StepsFDialog;

public class DecommissionSpoilMasterActivity extends BaseMasterActivity
        implements IRecyclerViewItemClick<OutgoInvoiceHead> {

    DecommissionSpoilMasterViewModel viewModel;
    StepsFDialog dialog;
    ActivityDecommissionSpoilMasterBinding binding;
    ImageView alertImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_decommission_spoil_master);
        init();
        initViewModel();
    }

    private void init(){
        setOkButton((Button) findViewById(R.id.a_master_ok));
        setCancelButton((Button) findViewById(R.id.a_master_cancel));


        setToolbar((Toolbar) findViewById(R.id.t_masters_toolbar));
        getToolbar().setTitle("Новое списание");


        CardView inventory_master_card_step_1 = findViewById(R.id.inventory_master_card_step_1);
        inventory_master_card_step_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        alertImageView = findViewById(R.id.alertImageView);
    }

    private void initViewModel(){
        viewModel = ViewModelProviders.of(this).get(DecommissionSpoilMasterViewModel.class);
        binding.setViewmodel(viewModel);
        if (viewModel.getState() == Constants.ViewModelStateEnum.LOADED) return;
        viewModel.Load();
    }

    public void showAlertImage(boolean isShow ){
        if (isShow) {
            alertImageView.setVisibility(View.VISIBLE);
        } else {
            alertImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void OkButtonClick() {
        super.OkButtonClick();
        try {
            viewModel.saveSelectedDocument();
            finish();
        } catch (NullPointerException e) {
            showAlertImage(true);
        }
    }

    @Override
    protected void CancelButtonClick() {
        super.CancelButtonClick();
        finish();
    }

    private void showDialog(){
        dialog = new StepsFDialog();
        dialog.setViewModel(viewModel);
        dialog.setTitle("Брак");
        dialog.show(getSupportFragmentManager(),"dsfds");
    }

    @Override
    public void OnClick(int pos, OutgoInvoiceHead innerItem) {
        showAlertImage(false);
        dialog.dismiss();
    }

    @Override
    public void OnCancel() {

    }
}
