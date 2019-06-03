package ru.a5x5retail.frontproductmanagement.newdocumentmaster.inventorymaster;

import android.app.DialogFragment;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.db.models.InventoryList;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.CallableQAsync;
import ru.a5x5retail.frontproductmanagement.db.query.create.CreateNewLocalInventoryQuery;
import ru.a5x5retail.frontproductmanagement.interfaces.IRecyclerViewItemClick;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.inventorymaster.dialogfragment.InventoryDialogFragment;


public class InventoryMasterActivity extends AppCompatActivity
implements IRecyclerViewItemClick<InventoryList>
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_master);
        init();
        initCardViewStep1();
        initViewModel();

    }

    private InventoryDialogFragment dialog;
    private InventoryMasterViewModel viewModel;
    private Toolbar toolbar;
    private Button a_inventory_master_cancel;
    private Button a_inventory_master_ok;

    /*
    *       Step 1
    */
    private CardView card_view_step_1;
    private TextView textview333;

    private void init(){
        a_inventory_master_cancel = findViewById(R.id.a_master_cancel);
        a_inventory_master_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        a_inventory_master_ok = findViewById(R.id.a_master_ok);
        a_inventory_master_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LiveData<InventoryList> selectedInventoryList = viewModel.getSelectedInventoryList();
                if (selectedInventoryList == null) {
                    return;
                }
                InventoryList value = viewModel.getSelectedInventoryList().getValue();
                if (value == null) {
                    return;
                }

                CreateNewLocalInventoryQuery query =
                        new CreateNewLocalInventoryQuery(
                                viewModel.getSelectedInventoryList().getValue().inventoryGuid,
                                        AppConfigurator.getDeviceId(InventoryMasterActivity.this),"master");
                query.addOnPostExecuteListener(new CallableQAsync.OnPostExecuteListener() {
                    @Override
                    public void onPostExecute() {
                        setResult(1);
                        finish();
                    }
                });
                query.ExecuteAsync();

            }
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


   private void initCardViewStep1(){
       card_view_step_1 = findViewById(R.id.inventory_master_card_step_1);
       textview333 = findViewById(R.id.textview333);
       card_view_step_1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

              /* TestDialog s = new TestDialog();
               s.show(getFragmentManager(),"dsfds");*/

               dialog = new InventoryDialogFragment();
               dialog.setListener(InventoryMasterActivity.this);
               dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
               dialog.setViewModel(viewModel);
               dialog.show(getFragmentManager(),"dsfds");

           }
       });
   }

    private void initViewModel(){

        viewModel =  ViewModelProviders.of(this).get(InventoryMasterViewModel.class);
        viewModel.getSelectedInventoryList().observe(this, new Observer<InventoryList>() {
            @Override
            public void onChanged(@Nullable InventoryList inventoryList) {
                textview333.setText(inventoryList.nameLong);
            }
        });
        try {
            viewModel.Load();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_document_master_menu,menu);
        return true;
    }*/

    @Override
    public void OnClick(int pos, InventoryList innerItem) {
        viewModel.setSelectedInventoryList(innerItem);
        dialog.dismiss();
    }

    @Override
    public void OnCancel() {

    }
}
