package ru.a5x5retail.frontproductmanagement.checkinglistinc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.BaseAppCompatActivity;
import ru.a5x5retail.frontproductmanagement.base.TestFragment;
import ru.a5x5retail.frontproductmanagement.broadcast.SystemBroadCast;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.fragments.CheckingListIncPositionFragment;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.fragments.CheckingListMarksFragment;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.fragments.DateOfManufactorFragment;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.document.DictionaryGoodsActivity;
import ru.a5x5retail.frontproductmanagement.filters.skufilter.SkuFilterActivity;

import static ru.a5x5retail.frontproductmanagement.broadcast.SystemBroadCast.SCN_CUST_EX_SCODE;
import static ru.a5x5retail.frontproductmanagement.сheckinglist.broadcast.SystemBroadCast.SCN_CUST_ACTION_SCODE;


public class CheckingListIncActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking_list_inc);
        init();
        initUi();
        initViewModel();
        loadViewModel();

    }

    private BottomNavigationView bottomNavigationView;

    private IReceiveScanerMessageListener receiveScanerMessageListener;

    @SuppressLint("RestrictedApi")
    private void init() {

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.goods_nav_item :
                        replaceFragment(CheckingListIncPositionFragment.newInstance(),false);
                        break;
                    case R.id.prod_date_item :
                        replaceFragment(DateOfManufactorFragment.newInstance(),false);
                        break;
                    case R.id.mark_info_item :
                        replaceFragment(CheckingListMarksFragment.newInstance(),false);
                        break;

                }
                return true;
            }
        });
    }
    private void initUi() {

        if (isFirstStart()) {
            replaceFragment(CheckingListIncPositionFragment.newInstance(),false);
        }
    }

    CheckingListIncViewModel viewModel;

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(CheckingListIncViewModel.class);
        if (isFirstStart()) {
            viewModel.selectedCheckingListHead = getIntent().getParcelableExtra(Constants.PACKINGLISTHEAD_CONST);
        }
    }

    private void loadViewModel(){
        try {
            viewModel.Load();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void viewFragment(TestFragment fragment) {

    }

    @SuppressLint("RestrictedApi")
    private void replaceFragment(Fragment fragment, boolean useBackStack) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fmTra = fragmentManager.beginTransaction();
        fmTra.replace(R.id.fragment_frame_layout, fragment);
        if (useBackStack) {
            fmTra.addToBackStack(null);
        }

        fmTra.commit();
    }


    private BroadcastReceiver scanDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = "";
            if (intent.getAction().equals(SystemBroadCast.SCN_CUST_ACTION_SCODE)) {
                message = intent.getStringExtra(SCN_CUST_EX_SCODE);
                if (receiveScanerMessageListener != null) {
                    receiveScanerMessageListener.receiveMessage(message);
                }
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(SCN_CUST_ACTION_SCODE);
        registerReceiver(scanDataReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(scanDataReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_a_checking_list_inc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                finish();
                return true;
            case R.id.action_dictionary :
                Intent intentdict = new Intent(this, SkuFilterActivity.class);
                intentdict.putExtra(Constants.FILTER_DATA_REQUEST_CONST,viewModel.selectedCheckingListHead.Guid);
                startActivityForResult(intentdict,234);
                return true;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 234 :
                if (resultCode == Activity.RESULT_OK) {
                    CodeInfo selectedSku = data.getParcelableExtra(Constants.FILTER_DATA_RESPONSE_CONST);
                    try {
                        viewModel.addNewSku(selectedSku.code);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    public void setReceiveScanerMessageListener(IReceiveScanerMessageListener receiveScanerMessageListener) {
        this.receiveScanerMessageListener = receiveScanerMessageListener;
    }

    public interface IReceiveScanerMessageListener {
     void receiveMessage(String message);
    }
}
