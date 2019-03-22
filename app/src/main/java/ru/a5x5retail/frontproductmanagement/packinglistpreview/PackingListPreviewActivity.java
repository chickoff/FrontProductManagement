package ru.a5x5retail.frontproductmanagement.packinglistpreview;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.UUID;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.BaseAppCompatActivity;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.help.HelpDialog;
import ru.a5x5retail.frontproductmanagement.сheckinglist.document.CheckingListGoodsActivity;

import static ru.a5x5retail.frontproductmanagement.configuration.Constants.PACKINGLISTHEAD_CONST;

public class PackingListPreviewActivity extends BaseAppCompatActivity {
    PackingListPreviewViewModel viewModel;
    TextView a_packing_list_preview_textview_1;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packing_list_preview);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        initViewModel();
        setView();
    }

    private void init(){
        a_packing_list_preview_textview_1 = findViewById(R.id.a_packing_list_preview_textview_1);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createIntent();
            }
        });
    }

    private void initViewModel(){
        viewModel = ViewModelProviders.of(this).get(PackingListPreviewViewModel.class);
        if (!viewModel.isInitialized()){
            viewModel.head = getIntent().getParcelableExtra(PACKINGLISTHEAD_CONST);
            viewModel.setInitialized(true);
        }
    }

    private void setView(){
        if (viewModel.head != null)
            a_packing_list_preview_textview_1.setText(viewModel.head.NameDoc);
    }

    private void createIntent(){
        Intent intent1 = new Intent(this, CheckingListGoodsActivity.class);
        intent1.putExtra("gggg", UUID.fromString(viewModel.head.Guid));
        startActivity(intent1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.packing_list_preview_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home :
                finish();
                break;

            case R.id.itemHelp :
                HelpDialog hd = new HelpDialog();
                hd.showFor(this.getSupportFragmentManager(),this.getClass());
                break;
            case R.id.a_pl_preview_upd_item_1 :
                try {
                    viewModel.UpdateInRr(viewModel.head.Guid, Constants.getCurrentTypeOfDocument());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                return true;
        }
        return true;
    }
}
