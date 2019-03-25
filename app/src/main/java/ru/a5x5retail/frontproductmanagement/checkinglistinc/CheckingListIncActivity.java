package ru.a5x5retail.frontproductmanagement.checkinglistinc;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.BaseAppCompatActivity;
import ru.a5x5retail.frontproductmanagement.base.TestFragment;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.fragments.CheckingListIncPositionFragment;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.fragments.CheckingListMarksFragment;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.fragments.DateOfManufactorFragment;


public class CheckingListIncActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking_list_inc);
        init();
        initUi();
        initViewModel();

    }

    private BottomNavigationView bottomNavigationView;

    private void init() {
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

    private void initViewModel() {

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
}
