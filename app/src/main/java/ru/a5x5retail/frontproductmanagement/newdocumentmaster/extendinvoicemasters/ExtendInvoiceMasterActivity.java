package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorInfo;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.read.GetExtendedContractorInfoQuery;
import ru.a5x5retail.frontproductmanagement.filters.filterfragments.ContractorFilter.ContractorFilterFragment;
import ru.a5x5retail.frontproductmanagement.filters.filterfragments.ContractorFilter.IFilterFragmentCompleteListener;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments.ExtendedContractorInfoFragment;


public class ExtendInvoiceMasterActivity extends AppCompatActivity
implements IFilterFragmentCompleteListener<ContractorInfo>
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_invoice_master);
        init();
    }

    private FrameLayout fragmentLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private static boolean f;


    @SuppressLint("RestrictedApi")
    private void init() {

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        fragmentLayout = findViewById(R.id.fragmentLayout);

        if (!f) {
            replaceFragment(ContractorFilterFragment.newInstance("dddddf","ddf"));
        }

      /*  try {
            MsSqlConnection con = new MsSqlConnection();
            GetExtendedContractorInfoQuery q = new GetExtendedContractorInfoQuery(con.getConnection());
            q.Execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fmTra = fragmentManager.beginTransaction();
        fmTra.replace(R.id.fragmentLayout, fragment);
        fmTra.addToBackStack(null);
        fmTra.commit();
        f = true;
    }

    @Override
    public void setResult(ContractorInfo result) {

        replaceFragment(ExtendedContractorInfoFragment.newInstance(result.guid));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 1) {
                    fragmentManager.popBackStack();
                } else f = false;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
