package ru.a5x5retail.frontproductmanagement.packinglistpreview;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.UUID;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.BaseAppCompatActivity;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.CheckingListIncActivity;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db.models.AcceptResult;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.db.models.QueryReturnCode;
import ru.a5x5retail.frontproductmanagement.help.HelpDialog;
import ru.a5x5retail.frontproductmanagement.сheckinglist.document.CheckingListGoodsActivity;

import static ru.a5x5retail.frontproductmanagement.configuration.Constants.PACKINGLISTHEAD_CONST;

public class PackingListPreviewActivity extends BaseAppCompatActivity {
    PackingListPreviewViewModel viewModel;
    //TextView a_packing_list_preview_textview_1;

    FrameLayout fragment_frame_layout;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packing_list_preview);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        initUi();
        initViewModel();
        setView();
    }

    private void init(){
      //  a_packing_list_preview_textview_1 = findViewById(R.id.a_packing_list_preview_textview_1);

       /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createIntent();
            }
        });*/

        fragment_frame_layout = findViewById(R.id.fragment_frame_layout);
    }

    private void initUi() {
        if (isFirstStart()) {
            replaceFragment(PreviewFragment.newInstance(),false);
        }
    }


    private void replaceFragment(Fragment fragment, boolean useBackStack) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fmTra = fragmentManager.beginTransaction();
        fmTra.replace(R.id.fragment_frame_layout, fragment);
        if (useBackStack) {
            fmTra.addToBackStack(null);
        }

        fmTra.commit();
    }

    private void initViewModel(){
        viewModel = ViewModelProviders.of(this).get(PackingListPreviewViewModel.class);
        if (!viewModel.isInitialized()){
            viewModel.head = getIntent().getParcelableExtra(PACKINGLISTHEAD_CONST);
            viewModel.setInitialized(true);
        }
    }

    private void setView(){
        //if (viewModel.head != null)
            //a_packing_list_preview_textview_1.setText(viewModel.head.NameDoc);
    }

    /*private void createIntent(){
       if (Constants.getCurrentDoc().getTypeOfDocument() == Constants.TypeOfDocument.OUTER_INCOME ||
           Constants.getCurrentDoc().getTypeOfDocument() == Constants.TypeOfDocument.INNER_INCOME) {
           Intent intent1 = new Intent(this, CheckingListIncActivity.class);
           intent1.putExtra(Constants.PACKINGLISTHEAD_CONST, viewModel.head);
           startActivity(intent1);
       } else {
           Intent intent1 = new Intent(this, CheckingListGoodsActivity.class);
           intent1.putExtra("gggg", UUID.fromString(viewModel.head.Guid));
           startActivity(intent1);
       }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.packing_list_preview_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home :

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.popBackStack();
                if (fragmentManager.getBackStackEntryCount() == 0) {
                    finish();
                }
                break;

            case R.id.itemHelp :
                HelpDialog hd = new HelpDialog();
                hd.showFor(this.getSupportFragmentManager(),this.getClass());
                break;
            case R.id.a_pl_preview_upd_item_1 :
                try {
                    QueryReturnCode r0 = viewModel.UpdateInRr(viewModel.head.Guid, Constants.getCurrentDoc().getTypeOfDocument());
                    if (r0.returnCode == 0) {
                        ProdManApp.Alerts.MakeToast("Синхронизация прошла успешно",0);
                    } else {
                        ProdManApp.Alerts.MakeToast(r0.eventMessage,0);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.m_accept_item :
                replaceFragment(AcceptFragment.newInstance(),true);
                break;
            default:
                return true;
        }
        return true;
    }
}
