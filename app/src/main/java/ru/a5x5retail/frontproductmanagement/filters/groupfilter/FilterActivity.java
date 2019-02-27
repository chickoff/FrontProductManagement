package ru.a5x5retail.frontproductmanagement.filters.groupfilter;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.components.threestatecheckbox.ThreeStateCheckbox;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.SkuGroup.SKU_STATE;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.fragments.ContractorFilter.ContractorFilterFragment;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.fragments.GroupFilter.GroupFilterFragment;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.fragments.GroupFilter.GroupFilterViewModel;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.fragments.ResultSku.ResultSkuFragment;

public class FilterActivity extends AppCompatActivity
implements IFilterCompleteListener<CodeInfo>
{
    BottomNavigationView navigationView;
    FilterViewModel viewModel;

    private DrawerLayout drawerLayout = null;

    NavigationView nav_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_filter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_find_in_page_black_24dp);


        drawerLayout = findViewById(R.id.drawerLayout);
        initViewModel();
       // init();


        nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setCheckable(true);
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();

                        Fragment fragment = null;

                        switch (menuItem.getItemId()){
                            case R.id.m_subgroup_filter :
                                fragment = GroupFilterFragment.newInstance("dddddf","ddf");
                               break;

                            case R.id.m_contractor_filter:
                                fragment = ContractorFilterFragment.newInstance("dddddf","ddf");
                                break;

                            case R.id.m_result_sku:
                                fragment = ResultSkuFragment.newInstance("dddddf","ddf");
                                break;

                            case R.id.m_exit:
                                setResult(RESULT_CANCELED,null);
                                finish();
                                return false;

                            default: return true;

                        }

                        replaceFragment(fragment);
                        /*FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frAg, fragment).commit();*/

                        return true;
                    }
                }
        );
    }



   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/


    private RecyclerView recyclerView;
    private BasicRecyclerViewAdapter<SkuGroup> adapter ;

/*    @SuppressLint("RestrictedApi")
    private void init() {
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        //navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectNavigationView(menuItem);
                return true;
            }
        });

        adapter = new BasicRecyclerViewAdapter<>();
        SkuGroupViewHolderFactory factory = new SkuGroupViewHolderFactory();
        adapter.setHolderFactory(factory);
        adapter.setLayout(R.layout.item_group_filter_rv);
        adapter.setSourceList(viewModel.getCurrentGroup().childList);
        adapter.setShortClickListener(new BasicRecyclerViewAdapter.IRecyclerViewItemShortClickListener<SkuGroup>() {
            @Override
            public void OnShortClick(int pos, SkuGroup innerItem) {
                shortClick(pos,innerItem);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SkuGroupRvDecoration());
    }*/

    private void initViewModel(){
        viewModel = ViewModelProviders.of(this).get(FilterViewModel.class);
        if (viewModel.getState() == Constants.ViewModelStateEnum.LOADED) {
            return;
        }
        try {
            viewModel.Load();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

/*    private void updAdapter(){
        if (viewModel.getCurrentGroup() == null) return;
        adapter.setSourceList(viewModel.getCurrentGroup().childList);
        recyclerView.getAdapter().notifyDataSetChanged();
    }*/

    private void selectNavigationView(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.m_subgroup_filter :
                break;
        }
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

/*    private void shortClick(int pos, SkuGroup innerItem){
        if (innerItem.childList == null || innerItem.childList.size() == 0) {
            return;
        }
        viewModel.setCurrentGroup(innerItem);
        updAdapter();
    }*/

 /*   private void backSpace(){
        if (viewModel.getCurrentGroup() == null) return;
        if (viewModel.getCurrentGroup().getParent() == null)  return;
        viewModel.setCurrentGroup(viewModel.getCurrentGroup().getParent());
        updAdapter();
    }*/

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

        /*Fragment fragment = ResultSkuFragment.newInstance("dddddf","ddf");
        replaceFragment(fragment);*/
    }

    private void replaceFragment(Fragment fragment) {

       /* MenuItem menuItem = nav_view.getMenu().findItem(R.id.m_result_sku);
        menuItem.setCheckable(true);
        menuItem.setChecked(true);*/

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frAg, fragment).commit();
    }


    public class SkuGroupViewHolder extends BasicViewHolder<SkuGroup> {

        private TextView i_group_filter_text_view_1;
        private ThreeStateCheckbox i_group_filter_switch_1;

        public SkuGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View view){
            i_group_filter_text_view_1 = view.findViewById(R.id.i_group_filter_text_view_1);
            i_group_filter_switch_1 =  view.findViewById(R.id.i_group_filter_switch_1);
        }

        private void reset() {
            i_group_filter_switch_1.setOnCheckedChangeListener(null);
        }

        @Override
        public void setSource(final SkuGroup source) {
            final SkuGroup src = source;
            reset();
            i_group_filter_text_view_1.setText(source.nameLong);
            i_group_filter_switch_1.setChecked(source.isChecked());
            i_group_filter_switch_1.setUnknownState(src.getState() == SKU_STATE.PARTIAL);
            i_group_filter_switch_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    src.setChecked(isChecked);
                    setState(src);
                }
            });
        }

        private void setState(final SkuGroup source){
            i_group_filter_switch_1.setUnknownState(source.getState() == SKU_STATE.PARTIAL);
        }
    }

    public class SkuGroupViewHolderFactory extends BasicViewHolderFactory {
        @Override
        public BasicViewHolder getNewInstance(View itemView) {
            return new SkuGroupViewHolder(itemView);
        }
    }

    public class SkuGroupRvDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
             outRect.bottom = 5;
        }
    }
}


