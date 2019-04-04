package ru.a5x5retail.frontproductmanagement.сheckinglist.document;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import ru.a5x5retail.frontproductmanagement.BInfo;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.document.DictionaryGoodsActivity;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.FilterActivity;
import ru.a5x5retail.frontproductmanagement.сheckinglist.Dialog.CheckingListGoodsItemDialog;
import ru.a5x5retail.frontproductmanagement.сheckinglist.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.models.CheckingListGoods;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.models.SKUContext;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.query.CheckingListGoodsCheckedAllQuery;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.query.CheckingListGoodsDeleteQuery;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.query.CheckingListGoodsEditQuery;
import ru.a5x5retail.frontproductmanagement.сheckinglist.db.query.CheckingListGoodsGetSKUInfo;
import ru.a5x5retail.frontproductmanagement.сheckinglist.document.viewmodel.DocumentItemsViewModel;
import ru.a5x5retail.frontproductmanagement.сheckinglist.interfaces.IRecyclerViewItemClick;

import static ru.a5x5retail.frontproductmanagement.сheckinglist.broadcast.SystemBroadCast.SCN_CUST_ACTION_SCODE;
import static ru.a5x5retail.frontproductmanagement.сheckinglist.broadcast.SystemBroadCast.SCN_CUST_EX_SCODE;


public class CheckingListGoodsActivity extends AppCompatActivity
implements IRecyclerViewItemClick<CheckingListGoods>
{
//CheckingListGoodsRvAdapter

    private UUID checkingListHeadGUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking_list_goods);


        checkingListHeadGUID = (UUID)getIntent().getSerializableExtra("gggg");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViewModel();
        initRecyclerView();


        IntentFilter intentFilter = new IntentFilter(SCN_CUST_ACTION_SCODE);
        registerReceiver(scanDataReceiver,intentFilter);

       /* initFragment();

        initFloatingButton();*/




       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(scanDataReceiver);

    }
    //обьявляем
    private DocumentItemsViewModel viewModel;

    //создаем вью модель в провайдере
    private void initViewModel(){
        viewModel = ViewModelProviders.of(this).get(DocumentItemsViewModel.class);
        loadViewModel();
    }

    private void loadViewModel(){
        if (viewModel.getState() == Constants.ViewModelStateEnum.LOADED)
            return;
       // UUID rrt =checkingListHeadGUID;
        viewModel.Load(checkingListHeadGUID);
    }


    private void initRecyclerView(){
        RecyclerView docItemsRecyclerView = findViewById(R.id.recyclerViewGoods);
        docItemsRecyclerView.setAdapter(new CheckingListGoodsRvAdapter(viewModel.getGoodsList(),this));
       // docItemsRecyclerView.addItemDecoration(new PackingListItemsRvDecoration());
    }


   // Чиков, 13:48
    // прои закратие справочника
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 23) {
            if (resultCode == RESULT_OK) {
                addCodesByFilter(data);
            }
        }
        reLoadViewModel();
    }

    private void addCodesByFilter(Intent data) {
        ArrayList<CodeInfo> vv = data.getParcelableArrayListExtra("RE");
        viewModel.addCodes(checkingListHeadGUID,vv);

    }

    private void reLoadViewModel() {
        viewModel.Load(checkingListHeadGUID);
        initRecyclerView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_delete) {

            MsSqlConnection con = new MsSqlConnection();
            CheckingListGoodsDeleteQuery query2 = new CheckingListGoodsDeleteQuery(checkingListHeadGUID
                    );
            con.CallQuery(query2);
            viewModel.Load(checkingListHeadGUID);
            initRecyclerView();
            return true;

        }
        if (id == R.id.action_dictionary) {

            Intent intentdict = new Intent(this, DictionaryGoodsActivity.class);
            intentdict.putExtra("checkingListHeadGUID",checkingListHeadGUID.toString());


          startActivityForResult(intentdict,234);
           // startActivity(intentdict);

            //intent2.putExtra("printprice","9EEDD00E-1DBF-47A8-BB19-5DDA62C49097");

            return true;
        }


        if (id == R.id.m_find_code) {
            Intent intentdict = new Intent(this, FilterActivity.class);
            startActivityForResult(intentdict,23);

        }
        if (id == R.id.action_select_alll) {
            MsSqlConnection con = new MsSqlConnection();

            CheckingListGoodsCheckedAllQuery query1 = new CheckingListGoodsCheckedAllQuery(checkingListHeadGUID.toString());
            con.CallQuery(query1);
            viewModel.Load(checkingListHeadGUID);
            initRecyclerView();


            return true;

        }



        /*
          MsSqlConnection con = new MsSqlConnection();
                    CheckingListGoodsEditQuery query2 = new CheckingListGoodsEditQuery(innerItem.CheckingListHeadGuid
                            ,innerItem.Code
                            ,bigQty
                            ,0);
                    con.CallQuery(query2);
        */

        return super.onOptionsItemSelected(item);
    }


    private BroadcastReceiver scanDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SCN_CUST_ACTION_SCODE)) {
                try {
                    String message = "";
                    message = intent.getStringExtra(SCN_CUST_EX_SCODE).toString();
                    // arrayAdapter.add(message);
                    // tvTextView.setText(message);
                      rowsAdd(message);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("in", e.toString());
                }
            }
        }
    };

    private void rowsAdd(String barcodeStr) {
        MsSqlConnection con = new MsSqlConnection();

        BInfo barcode = new BInfo(barcodeStr);
        CheckingListGoodsGetSKUInfo q1 = new CheckingListGoodsGetSKUInfo(barcodeStr);
        con.CallQuery(q1);
        barcode.setSkuContext(q1.getSKUInfoList());

        if (barcode.isLocal()) {
            if(barcode.isWeightAvailable()){
                CheckingListGoodsEditQuery query2 = new CheckingListGoodsEditQuery(checkingListHeadGUID,barcode.getSkuContext().Code,barcode.getLocalWeight(),1);
                con.CallQuery(query2);
                return;
            }
        }

        CheckingListGoodsEditQuery query2 = new CheckingListGoodsEditQuery(checkingListHeadGUID, barcode.getSkuContext().Code, BigDecimal.valueOf(0), 2);
        con.CallQuery(query2);

        viewModel.Load(checkingListHeadGUID);
        initRecyclerView();
        viewModel.getGoodsList().get(0);
        openDialogF(viewModel.getGoodsList().get(0));
    }

    @SuppressLint("ValidFragment")
    private  void openDialogF(CheckingListGoods innerItem)
    {
        CheckingListGoodsItemDialog dialog = new CheckingListGoodsItemDialog(){

            @Override
            public void onDetach() {
                super.onDetach();
                viewModel.Load(checkingListHeadGUID);
                initRecyclerView();

            }
        };

        dialog.setMode(1);
        dialog.setQty(innerItem);
        dialog.show(((Activity)this).getFragmentManager(), "MyCustomDialog");




    }

    @SuppressLint("ValidFragment")
    @Override
    public void OnClick(int pos, CheckingListGoods innerItem) {


        CheckingListGoodsItemDialog dialog = new CheckingListGoodsItemDialog(){

            @Override
            public void onDetach() {
                super.onDetach();
                viewModel.Load(checkingListHeadGUID);
                initRecyclerView();

            }
        };
        dialog.setMode(0);//замешение и вывод общего значения
        dialog.setQty(innerItem);
        dialog.show(((Activity)this).getFragmentManager(), "MyCustomDialog");

    }
}
