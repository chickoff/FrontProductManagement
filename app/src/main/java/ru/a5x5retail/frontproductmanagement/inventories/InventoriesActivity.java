package ru.a5x5retail.frontproductmanagement.inventories;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.BaseAppCompatActivity;
import ru.a5x5retail.frontproductmanagement.broadcast.SystemBroadCast;
import ru.a5x5retail.frontproductmanagement.filters.actualassortmentfilter.ActualAssortmentFilterFragment;
import ru.a5x5retail.frontproductmanagement.filters.actualassortmentfilter.IActualAssortmentFilterCallListener;
import ru.a5x5retail.frontproductmanagement.filters.actualassortmentfilter.IActualAssortmentFilterCloseListener;
import ru.a5x5retail.frontproductmanagement.filters.assortmentfilter.AssortmentFilterFragment;
import ru.a5x5retail.frontproductmanagement.interfaces.IReceiveScanerMessageListener;
import ru.a5x5retail.frontproductmanagement.inventories.fragments.EditInventoryGoodsFragment;
import ru.a5x5retail.frontproductmanagement.inventories.fragments.EditInventoryStatementFragment;
import ru.a5x5retail.frontproductmanagement.inventories.fragments.InventoryPreviewFragment;
import ru.a5x5retail.frontproductmanagement.inventories.fragments.InventoryScanGoodsFragment;
import ru.a5x5retail.frontproductmanagement.inventories.fragments.InventorySheetsFragment;
import ru.a5x5retail.frontproductmanagement.inventories.fragments.InventorySheetsFragment.IEditInventoryGoodsListener;
import ru.a5x5retail.frontproductmanagement.inventories.fragments.InventoryStatementFragment;

import static ru.a5x5retail.frontproductmanagement.broadcast.SystemBroadCast.SCN_CUST_EX_SCODE;
import static ru.a5x5retail.frontproductmanagement.сheckinglist.broadcast.SystemBroadCast.SCN_CUST_ACTION_SCODE;

public class InventoriesActivity extends BaseAppCompatActivity
implements IInventoriesActivityView,
        IActualAssortmentFilterCallListener,
        IEditInventoryGoodsListener
{
    @InjectPresenter
    InventoriesActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventories);
    }

    @Override
    public void setInventoryStatementFragment() {
        replaceFragment(InventoryStatementFragment.newInstance(new InventoryStatementFragment.IInventoryStatementSelectedListener() {
            @Override
            public void onSelect() {
                presenter.setInventorySheetsFragment();
            }
        }), false);

    }

    @Override
    public void setInventorySheetsFragment() {
        replaceFragment(InventorySheetsFragment.newInstance(new InventorySheetsFragment.IInventorySheetsSelectedListener() {
            @Override
            public void onSelect() {
                presenter.setInventoryPreviewFragment();
            }
        }), true);

    }

    @Override
    public void setInventoryPreviewFragment() {
        replaceFragment(InventoryPreviewFragment.newInstance(new InventoryPreviewFragment.IInventoryPreviewFragmentListener() {
            @Override
            public void onSelect() {
                presenter.setInventoryScanGoodsFragment();
            }
        }), true);
    }

    @Override
    public void setEditInventoryStatementFragment() {
        replaceFragment(EditInventoryStatementFragment.newInstance(), true);

    }

    @Override
    public void setInventoryScanGoodsFragment() {
        replaceFragment(InventoryScanGoodsFragment.newInstance(), true);
    }

    @Override
    public void setEditInventoryGoodsFragment() {
        replaceFragment(EditInventoryGoodsFragment.newInstance(), true);
    }

    @Override
    public void setActualAssortmentFilterFragment(ActualAssortmentFilterFragment.IActualAssortmentFilterResultListener resultListener) {
        replaceFragment(ActualAssortmentFilterFragment.newInstance(new IActualAssortmentFilterCloseListener() {
            @Override
            public void actualAssortmentFilterClose() {
                back();
            }
        },resultListener), true);
    }


    private int back(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        return fragmentManager.getBackStackEntryCount();
    }

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home :
                if (back() == 0) {
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
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

    private IReceiveScanerMessageListener receiveScanerMessageListener;
    public void setReceiveScanerMessageListener(IReceiveScanerMessageListener receiveScanerMessageListener) {
        this.receiveScanerMessageListener = receiveScanerMessageListener;
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
    public void actualAssortmentFilterCall(ActualAssortmentFilterFragment.IActualAssortmentFilterResultListener resultListener) {
        presenter.setActualAssortmentFilterFragment(resultListener);
    }

    @Override
    public void onEditInventoryGoods() {
        presenter.setEditInventoryGoodsFragment();
    }
}
