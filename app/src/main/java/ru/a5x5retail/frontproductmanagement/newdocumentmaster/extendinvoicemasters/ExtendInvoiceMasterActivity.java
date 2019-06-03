package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.MenuItem;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.BaseAppCompatActivity;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorInfo;
import ru.a5x5retail.frontproductmanagement.db_local.ProjectMap;
import ru.a5x5retail.frontproductmanagement.filters.filterfragments.contractorfilter.ContractorFilterFragment;
import ru.a5x5retail.frontproductmanagement.filters.filterfragments.contractorfilter.IFilterFragmentCompleteListener;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments.ExtendedContractorInfoFragment;




public class ExtendInvoiceMasterActivity extends BaseAppCompatActivity
implements IFilterFragmentCompleteListener<ContractorInfo>,IExtendInvoiceMasterView
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_invoice_master);
        initUi();

    }

    private SwipeRefreshLayout mSwipeRefreshLayout;


    @InjectPresenter
    ExtendInvoiceMasterPresenter presenter;




    @SuppressLint("RestrictedApi")
    private void initUi() {
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
               // loadViewModel();
            }
        });
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
        presenter.setSelectedContractor(result);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.loadIncomeList();
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

    @Override
    public void showContractorFiterView() {
        replaceFragment(ContractorFilterFragment.newInstance(),false);
    }

    @Override
    public void showExtendedContractorInfoView() {
        replaceFragment(ExtendedContractorInfoFragment.newInstance(), true);
    }
}
