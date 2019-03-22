package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.BaseAppCompatActivity;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorInfo;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetExtendedContractorInfoQuery;
import ru.a5x5retail.frontproductmanagement.filters.filterfragments.ContractorFilter.ContractorFilterFragment;
import ru.a5x5retail.frontproductmanagement.filters.filterfragments.ContractorFilter.IFilterFragmentCompleteListener;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments.ExtendedContractorInfoFragment;


public class ExtendInvoiceMasterActivity extends BaseAppCompatActivity
implements IFilterFragmentCompleteListener<ContractorInfo>
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_invoice_master);
        initUi();
        initViewModel();
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void firstInit() {
        super.firstInit();
    }


    @SuppressLint("RestrictedApi")
    private void initUi() {
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                loadViewModel();
            }
        });

        if (isFirstStart()) {
            replaceFragment(ContractorFilterFragment.newInstance("dddddf","ddf"),false);
        }
    }

    @SuppressLint("RestrictedApi")
    private void replaceFragment(Fragment fragment,boolean useBackStack) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fmTra = fragmentManager.beginTransaction();
        fmTra.replace(R.id.fragmentLayout, fragment);
        if (useBackStack) {
            fmTra.addToBackStack(null);
        }

        fmTra.commit();
    }

    @Override
    public void setResult(ContractorInfo result) {
        viewModel.setExternalContractorGuid(result.guid);
        loadViewModel();
        replaceFragment(ExtendedContractorInfoFragment.newInstance(), true);
    }

    private ExtendedContractorInfoViewModel viewModel;


    private void initViewModel() {
            viewModel = ViewModelProviders.of(this).get(ExtendedContractorInfoViewModel.class);

            if (!viewModel.isInitialized()) {
                viewModel.setInitialized(true);
            }

            if (viewModel.getState() == Constants.ViewModelStateEnum.LOADED) {
                return;
            }


       // loadViewModel();
    }

    private void loadViewModel() {
        try {
            viewModel.Load();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadViewModel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            int a = 0;
            a = a;
        }

        else {
            int a = 0;
            a = a;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.popBackStack();
                if (fragmentManager.getBackStackEntryCount() == 0) {
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
