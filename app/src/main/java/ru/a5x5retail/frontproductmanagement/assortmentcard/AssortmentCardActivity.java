package ru.a5x5retail.frontproductmanagement.assortmentcard;

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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.BaseAppCompatActivity;
import ru.a5x5retail.frontproductmanagement.base.BaseFragment;
import ru.a5x5retail.frontproductmanagement.broadcast.SystemBroadCast;
import ru.a5x5retail.frontproductmanagement.db.models.DictionaryListGoods;
import ru.a5x5retail.frontproductmanagement.filters.assortmentfilter.AssortmentFilterFragment;
import ru.a5x5retail.frontproductmanagement.interfaces.IReceiveScanerMessageListener;

import static ru.a5x5retail.frontproductmanagement.broadcast.SystemBroadCast.SCN_CUST_EX_SCODE;

public class AssortmentCardActivity extends BaseAppCompatActivity
        implements IAssortmentCardView,
        AssortmentFilterFragment.IAssortmentFilterResultListener
{

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assortment_card);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @InjectPresenter
    AssortmentCardActivityPresenter presenter;
    FrameLayout fragment_frame_layout;
    private void initUi() {
        fragment_frame_layout = findViewById(R.id.fragment_frame_layout);
    }

    @Override
    public void viewSkuInfo() {
        replaceFragment(SkuInfoFragment.newInstance(),false);
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


   /* private IReceiveScanerMessageListener receiveScanerMessageListener;
    public void setReceiveScanerMessageListener(IReceiveScanerMessageListener receiveScanerMessageListener) {
        this.receiveScanerMessageListener = receiveScanerMessageListener;
    }*/
    private BroadcastReceiver scanDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = "";
            if (intent.getAction().equals(SystemBroadCast.SCN_CUST_ACTION_SCODE)) {
                message = intent.getStringExtra(SCN_CUST_EX_SCODE);
                presenter.setBarcode(message);
                /*if (receiveScanerMessageListener != null) {
                    receiveScanerMessageListener.receiveMessage(message);
                }*/
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(SystemBroadCast.SCN_CUST_ACTION_SCODE);
        registerReceiver(scanDataReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(scanDataReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assortment_card, menu);
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof BaseFragment) {
                ((BaseFragment)fragment).onKeyUp(keyCode,event);
            }

        }
        return super.onKeyUp(keyCode, event);

    }


    private int back(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        return fragmentManager.getBackStackEntryCount();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home :
                if (back() == 0) {
                    finish();
                }
                break;

            case R.id.assortment_filter_item :
                replaceFragment(AssortmentFilterFragment.newInstance(),true);
                break;
            default:
                return true;
        }
        return true;
    }

    @Override
    public void assortmentFilterResult(DictionaryListGoods sku) {
        back();
        presenter.setBarcode(String.valueOf(sku.Code));
    }
}
