package ru.a5x5retail.frontproductmanagement.packinglistitems;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import ru.a5x5retail.frontproductmanagement.DocType;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.adapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.base.BaseAppCompatActivity;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.invoicemasters.decommissionspoilmaster.DecommissionSpoilMasterActivity;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.invoicemasters.externalincomemaster.ExternalIncomeMasterActivity;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.invoicemasters.innerincomemaster.InnerIncomeMasterActivity;
import ru.a5x5retail.frontproductmanagement.packinglistitems.viewmodel.PackingListItemsViewModel;
import ru.a5x5retail.frontproductmanagement.interfaces.IRecyclerViewItemClick;

import ru.a5x5retail.frontproductmanagement.newdocumentmaster.inventorymaster.InventoryMasterActivity;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.packinglistpreview.PackingListPreviewActivity;

import static ru.a5x5retail.frontproductmanagement.configuration.Constants.PACKINGLISTHEAD_CONST;

public class PackingListItemsActivity extends BaseAppCompatActivity
        implements IRecyclerViewItemClick<CheckingListHead> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packing_list_items);
        init();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private RecyclerView docItemsRecyclerView;
    private BasicRecyclerViewAdapter<CheckingListHead> adapter ;
    private FloatingActionButton fab;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PackingListItemsViewModel viewModel;

    @SuppressLint("RestrictedApi")
    private void init() {
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createIntent();
            }
        });

        docItemsRecyclerView = findViewById(R.id.DocItemsRecyclerView);

        initViewModel();
        initRecyclerView();

        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                reLoadViewModel();
            }
        });
    }

    private  void initRecyclerView(){

        adapter = new BasicRecyclerViewAdapter<>();
        CheckingListHeadViewHolderFactory factory = new CheckingListHeadViewHolderFactory();
        adapter.setHolderFactory(factory);
        adapter.setLayout(R.layout.item_packing_list_items_rv);
        adapter.setSourceList(viewModel.getHeadList());
        adapter.setShortClickListener(new BasicRecyclerViewAdapter.IRecyclerViewItemShortClickListener<CheckingListHead>() {
            @Override
            public void OnShortClick(int pos, CheckingListHead innerItem) {
                ProdManApp.Activities
                        .createPackingListPreviewActivity
                                (PackingListItemsActivity.this,
                                        innerItem,viewModel.getTypeOfDoc().getIndex());
            }
        });
        docItemsRecyclerView.setAdapter(adapter);
        docItemsRecyclerView.addItemDecoration(new PackingListItemsRvDecoration());
    }

    private void initViewModel() {
        int typeDocInt = getIntent().getIntExtra(Constants.TYPEOFDOCUMENT_CONST,-1);
        viewModel = ViewModelProviders.of(this).get(PackingListItemsViewModel.class);
        viewModel.setTypeOfDoc(Constants.TypeOfDocument.getByOrd(typeDocInt));
        DocType td = AppConfigurator.getTypeDocByType(viewModel.getTypeOfDoc());
        this.setTitle(td.getShortName());
        loadViewModel();
    }

    private void loadViewModel() {
        if (viewModel.getState() == Constants.ViewModelStateEnum.LOADED) {
            return;
        }
        loadVm();
    }

    private void reLoadViewModel() {
        loadVm();
        adapter.setSourceList(viewModel.getHeadList());
        docItemsRecyclerView.getAdapter().notifyDataSetChanged();
    }

    private void loadVm(){
        try {
            viewModel.Load();
        } catch (SQLException e) {
            e.printStackTrace();
            setErrorToast(e,Constants.Messages.SQL_EXEPTION_MSG);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("HardwareIds")
    private void createIntent() {


       /* if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }*/
        switch (viewModel.getTypeOfDoc()){
            case PARTIAL_INVENTORY:
                CreateIntent(InventoryMasterActivity.class,viewModel.getTypeOfDoc());
                break;

            case INNER_INCOME:
                CreateIntent(InnerIncomeMasterActivity.class,viewModel.getTypeOfDoc());
                break;

            case OUTER_INCOME:
                CreateIntent(ExternalIncomeMasterActivity.class,viewModel.getTypeOfDoc());
                break;

            case DISCARD:
                CreateIntent(DecommissionSpoilMasterActivity.class,viewModel.getTypeOfDoc());
                break;
        }
    }

    private void CreateIntent(Class<?> c,Constants.TypeOfDocument td){
        ProdManApp.Activities
                .createNewDocumentActivity(this,c,td.getIndex(),1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.packing_list_items_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.itemUpd :
                reLoadViewModel();
                return true;
            case android.R.id.home :
                finish();
                return true;
        }
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public void OnClick(int pos, CheckingListHead innerItem) {
        ProdManApp.Activities
                .createPackingListPreviewActivity(this,innerItem,viewModel.getTypeOfDoc().getIndex());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        reLoadViewModel();
    }


    public class CheckingListHeadViewHolder extends BasicViewHolder<CheckingListHead> {

        private TextView nameDocTextView;
        private TextView statusTextView;

        public CheckingListHeadViewHolder(@NonNull View itemView) {
            super(itemView);
            nameDocTextView = itemView.findViewById(R.id.nameDocTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView)  ;
        }

        @Override
        public void setSource(CheckingListHead source) {
            nameDocTextView.setText(source.NameDoc);
            statusTextView.setText(source.StatusID == "0" ? "ЗАКРЫТО":"ОТКРЫТО");
        }
    }

    public class CheckingListHeadViewHolderFactory extends BasicViewHolderFactory {
        @Override
        public BasicViewHolder getNewInstance(View itemView) {
            return new CheckingListHeadViewHolder(itemView);
        }
    }

    public class PackingListItemsRvDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.top = 2;
        }
    }


}
