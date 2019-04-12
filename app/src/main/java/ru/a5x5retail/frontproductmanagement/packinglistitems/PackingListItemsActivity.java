package ru.a5x5retail.frontproductmanagement.packinglistitems;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.DocType;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.base.BaseAppCompatActivity;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db.models.CheckingListHead;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.ExtendInvoiceMasterActivity;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.invoicemasters.decommissionspoilmaster.DecommissionSpoilMasterActivity;

import ru.a5x5retail.frontproductmanagement.packinglistitems.viewmodel.PackingListItemsViewModel;
import ru.a5x5retail.frontproductmanagement.interfaces.IRecyclerViewItemClick;

import ru.a5x5retail.frontproductmanagement.newdocumentmaster.inventorymaster.InventoryMasterActivity;
import ru.a5x5retail.frontproductmanagement.R;

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

        if (Constants.getCurrentDoc() == null) {
            throw new NullPointerException("Object currentDoc not initialized!");
        }

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
        adapter.setShortClickListener(new IRecyclerViewItemShortClickListener<CheckingListHead>() {
            @Override
            public void OnShortClick(int pos, View view, CheckingListHead innerItem) {
                ProdManApp.Activities
                        .createPackingListPreviewActivity
                                (PackingListItemsActivity.this,
                                        innerItem);
            }
        });
        docItemsRecyclerView.setAdapter(adapter);
        docItemsRecyclerView.addItemDecoration(new PackingListItemsRvDecoration());
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(PackingListItemsViewModel.class);
        DocType td = Constants.getCurrentDoc();
        if (td != null) {
            this.setTitle(td.getShortName());
        }

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
        switch (Constants.getCurrentDoc().getTypeOfDocument()){
            case PARTIAL_INVENTORY:
                CreateIntent(InventoryMasterActivity.class,Constants.getCurrentDoc().getTypeOfDocument());
                break;

            case INNER_INCOME:
                CreateIntent(ExtendInvoiceMasterActivity.class,Constants.getCurrentDoc().getTypeOfDocument(),1);
                break;

            case OUTER_INCOME:
                CreateIntent(ExtendInvoiceMasterActivity.class,Constants.getCurrentDoc().getTypeOfDocument(),1);
                break;

            case DISCARD:
                CreateIntent(DecommissionSpoilMasterActivity.class,Constants.getCurrentDoc().getTypeOfDocument());
                break;
        }
    }

    private void CreateIntent(Class<?> c,Constants.TypeOfDocument td){
        ProdManApp.Activities
                .createNewDocumentActivity(this,c,1);
    }

    private void CreateIntent(Class<?> c,Constants.TypeOfDocument td, int requestCode){
        ProdManApp.Activities
                .createNewDocumentMasterActivity(this,c,td, requestCode);
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
                .createPackingListPreviewActivity(this,innerItem);
    }

    @Override
    public void OnCancel() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        reLoadViewModel();
    }


    public class CheckingListHeadViewHolder extends BasicViewHolder<CheckingListHead> {

        private TextView text_view_1,text_view_2,text_view_3,text_view_4;


        public CheckingListHeadViewHolder(@NonNull View itemView) {
            super(itemView);
            text_view_1 = itemView.findViewById(R.id.text_view_1);
            text_view_2 = itemView.findViewById(R.id.text_view_2);
            text_view_3 = itemView.findViewById(R.id.text_view_3);
            text_view_4 = itemView.findViewById(R.id.text_view_4);
        }

        @Override
        public void setSource(CheckingListHead source) {
            text_view_1.setText(source.NameDoc);
            text_view_2.setText(source.contractorName);
            text_view_3.setText(source.summ.toString());
            text_view_4.setText(source.summVat.toString());


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
