package ru.a5x5retail.frontproductmanagement.filters.groupfilter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.BaseAppCompatActivity;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.fragments.contractorfilter.ContractorFilterFragment;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.fragments.groupfilter.GroupFilterFragment;


public class FilterActivity extends BaseAppCompatActivity
implements  IFilterCompleteListener<CodeInfo>,
        IFilterView

{

    private DrawerLayout drawerLayout = null;
    NavigationView nav_view;

    @InjectPresenter
    FilterPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        presenter = presenter;

        setContentView(R.layout.activity_group_filter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_find_in_page_black_24dp);

        drawerLayout = findViewById(R.id.drawerLayout);
        initViewModel();

        nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setCheckable(true);
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        switch (menuItem.getItemId()){
                            case R.id.m_subgroup_filter :
                                presenter.showGroupFilter();
                               break;

                            case R.id.m_contractor_filter:
                                presenter.showContractorFilter();
                                break;

                            case R.id.m_result_sku:

                                break;

                            case R.id.m_exit:
                                setResult(RESULT_CANCELED,null);
                                finish();
                                return false;

                            default: return true;
                        }
                        return true;
                    }
                }
        );
    }

    private void initViewModel(){
        /*viewModel = ViewModelProviders.of(this).get(FilterViewModel.class);
        if (viewModel.getState() == Constants.ViewModelStateEnum.LOADED) {
            return;
        }
        try {
            viewModel.Load();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home :
                drawerLayout.openDrawer(GravityCompat.START);
               // backSpace();
                return true;
        }
        return true;
    }


    @Override
    public void setResult(List<CodeInfo> result) {

        if (result != null) {
            Intent intent = new Intent();
            ArrayList<CodeInfo> resultArray = (ArrayList<CodeInfo>) result;
            intent.putParcelableArrayListExtra("RE", resultArray);
            setResult(RESULT_OK, intent);
        } else {
            setResult (RESULT_CANCELED);
        }

        finish();
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frAg, fragment).commit();
    }


    @Override
    public void showContractorFilter() {
        replaceFragment( ContractorFilterFragment.newInstance());
    }

    @Override
    public void showGroupFilter() {
        replaceFragment( GroupFilterFragment.newInstance());
    }
}


