package ru.a5x5retail.frontproductmanagement.printprice.document;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.printprice.db.models.PrintPriceItem;
import ru.a5x5retail.frontproductmanagement.printprice.db.models.PrintPriceTypePrice;
import ru.a5x5retail.frontproductmanagement.printprice.db.models.SKUContext;
import ru.a5x5retail.frontproductmanagement.printprice.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.printprice.db.query.PrintPriceItemCheckedAllQuery;
import ru.a5x5retail.frontproductmanagement.printprice.db.query.PrintPriceItemDeleteQuery;
import ru.a5x5retail.frontproductmanagement.printprice.db.query.PrintPriceItemEditQuery;
import ru.a5x5retail.frontproductmanagement.printprice.db.query.PrintPriceItemGetSKUInfo;
import ru.a5x5retail.frontproductmanagement.printprice.db.query.PrintPriceListToPrintQuery;
import ru.a5x5retail.frontproductmanagement.printprice.document.Dialog.PrintPriceItemDialog;
import ru.a5x5retail.frontproductmanagement.printprice.document.Dialog.TypePriceDialog;
import ru.a5x5retail.frontproductmanagement.printprice.document.viewmodel.PrintPriceItemsViewModel;
import ru.a5x5retail.frontproductmanagement.printprice.document.viewmodel.PrintPriceTypePriceViewModel;
import ru.a5x5retail.frontproductmanagement.printprice.interfaces.IRecyclerViewItemClick;

import static ru.a5x5retail.frontproductmanagement.printprice.broadcast.SystemBroadCast.SCN_CUST_ACTION_SCODE;
import static ru.a5x5retail.frontproductmanagement.printprice.broadcast.SystemBroadCast.SCN_CUST_EX_SCODE;


public class PrintPriceActivity extends AppCompatActivity implements IRecyclerViewItemClick<PrintPriceTypePrice> {


    private String headerGUID,imeiID;
    //обьявляем viewModel
    private PrintPriceItemsViewModel viewModel;
    private PrintPriceTypePriceViewModel viewModelTypePrice;
    TypePriceDialog dialog;
    Button bTypePrice;
   // int TypePricePrint;
   PrintPriceTypePrice TypePricePrintItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_price);
        //получаем гуид заголовка от родительской Активити

        imeiID= AppConfigurator.getDeviceId(getApplicationContext());



        initViewModelTypePrice();

        initViewModel();
        initRecyclerView();

        IntentFilter intentFilter = new IntentFilter(SCN_CUST_ACTION_SCODE);
        registerReceiver(scanDataReceiver,intentFilter);

        // unregisterReceiver(scanDataReceiver);


        if(headerGUID==null) {
            headerGUID = viewModel.getHeadGuid();
        }

        bTypePrice= findViewById(R.id.print_price_type);

        bTypePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();

            }
        });


        String ddd= viewModelTypePrice.getPpitemList().get(0).NameLong;
        bTypePrice.setText("Ценник: "+ddd);
        this.TypePricePrintItem=viewModelTypePrice.getPpitemList().get(0);




    }

    private void showDialog(){
        dialog = new TypePriceDialog();
        dialog.setList(viewModelTypePrice.getPpitemList());
       // dialog.setTitle("Сторонний поствщик");
        dialog.show(getSupportFragmentManager(),"pptp");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(scanDataReceiver);

    }

    //создаем вью модель в провайдере
    private void initViewModel(){
        viewModel = ViewModelProviders.of(this).get(PrintPriceItemsViewModel.class);
        loadViewModel();
    }

    private void loadViewModel(){
        if (viewModel.getState() == Constants.ViewModelStateEnum.LOADED)
            return;

        viewModel.Load(imeiID,headerGUID);
        if(headerGUID==null) {
            headerGUID = viewModel.getHeadGuid();
        }

    }

    private void initViewModelTypePrice(){
        viewModelTypePrice = ViewModelProviders.of(this).get(PrintPriceTypePriceViewModel.class);
        loadViewModelTypePrice();
    }

    private void loadViewModelTypePrice() {
        if (viewModelTypePrice.getState() == Constants.ViewModelStateEnum.LOADED)
            return;
        viewModelTypePrice.Load(imeiID);

       // bTypePrice.setText("Ценник: "+innerItem.NameLong);


    }


    private void initRecyclerView(){
        RecyclerView docItemsRecyclerView = findViewById(R.id.recyclerViewItem);
        docItemsRecyclerView.setAdapter(new PrintPriceItemRvAdapter(viewModel.getItemList(),
                new IRecyclerViewItemClick<PrintPriceItem>() {
                    @Override
                    public void OnClick(int pos, PrintPriceItem innerItem) {
                        OnMMMMMMMMMMMMClick(pos,innerItem);
                    }
                }));

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_print_price, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_print) {
            bc_tosqlPrint bctosql = new bc_tosqlPrint();// this is the Asynctask, which is used to process in background to reduce load on app process
            bctosql.execute("");
            return true;
        }
        if (id == R.id.action_delete_price_item) {

            MsSqlConnection con = new MsSqlConnection();
            PrintPriceItemDeleteQuery query2 = new PrintPriceItemDeleteQuery(headerGUID);
            con.CallQuery(query2);

            viewModel.Load(imeiID,headerGUID);
            initRecyclerView();


            return true;
        }


        if (id == R.id.action_delete_all_price_item) {

            MsSqlConnection con = new MsSqlConnection();

            PrintPriceItemCheckedAllQuery query1 = new PrintPriceItemCheckedAllQuery(headerGUID);
            con.CallQuery(query1);

            PrintPriceItemDeleteQuery query2 = new PrintPriceItemDeleteQuery(headerGUID);
            con.CallQuery(query2);

            viewModel.Load(imeiID,headerGUID);
            initRecyclerView();

            return true;
        }
        if (id == R.id.action_select_all) {
            MsSqlConnection con = new MsSqlConnection();

            PrintPriceItemCheckedAllQuery query1 = new PrintPriceItemCheckedAllQuery(headerGUID);
            con.CallQuery(query1);

            viewModel.Load(imeiID,headerGUID);
            initRecyclerView();
            return true;

        }




        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ValidFragment")
    /*@Override*/
    public void OnMMMMMMMMMMMMClick(int pos, PrintPriceItem innerItem) {

        PrintPriceItemDialog dialog = new PrintPriceItemDialog()

        {

            @Override
            public void onDetach() {
                super.onDetach();
                viewModel.Load(imeiID,headerGUID);
                initRecyclerView();

            }
        };

        dialog.setQty(innerItem);
        dialog.show(this.getFragmentManager(),"PrintPriceItemDialog");


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

        SKUContext skuContext = new SKUContext();

        MsSqlConnection con = new MsSqlConnection();
        PrintPriceItemGetSKUInfo query =  new PrintPriceItemGetSKUInfo(barcodeStr);
        con.CallQuery(query);
        skuContext=query.getSKUInfoList();



        PrintPriceItemEditQuery query2 = new PrintPriceItemEditQuery(headerGUID,skuContext.Code, Integer.valueOf(1),1);
        con.CallQuery(query2);

        viewModel.Load(imeiID,headerGUID);
        initRecyclerView();


    }


    private String writeXml(List<PrintPriceItem> messages) {



        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            // serializer.startDocument("UTF-8", true);
            serializer.startTag("", "PricePrint");
            // serializer.attribute("", "number", String.valueOf(messages.size()));
            for (PrintPriceItem msg : messages) {
                //if (msg.Check) {
                    serializer.startTag("", "SKU");


                    serializer.startTag("", "code");
                    serializer.text(msg.Code.toString());
                    serializer.endTag("", "code");

                    serializer.startTag("", "qty");
                    serializer.text(msg.Qty.toString());
                    serializer.endTag("", "qty");

                    serializer.endTag("", "SKU");
               // }
            }
            serializer.endTag("", "PricePrint");
            serializer.endDocument();
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void OnClick(int pos, PrintPriceTypePrice innerItem) {

        this.TypePricePrintItem=innerItem;
        //здесь надо обновить надпись на кнопке
        bTypePrice.setText("Ценник: "+innerItem.NameLong);
        dialog.dismiss();

    }


    public class bc_tosqlPrint extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        String stringXml = writeXml(getChecked());








        private List<PrintPriceItem> getChecked() {

            List<PrintPriceItem> box = new ArrayList<PrintPriceItem>();


            for (PrintPriceItem p : viewModel.getItemList()) {
                // если в корзине
                if (p.Check) box.add(p);
            }
            return box;
        }



        @Override
        protected String doInBackground(String... params) {


            MsSqlConnection con = new MsSqlConnection();






            try {
               // con = connectionclass(un, pass, db, ip);        // Connect to database
                if (con == null) {
                    z = "Check Your Internet Access!";
                } else {
                    PrintPriceListToPrintQuery query =  new PrintPriceListToPrintQuery(stringXml,TypePricePrintItem.Id);
                    con.CallQuery(query);




                }
            } catch (Exception ex) {
                isSuccess = false;
                z = ex.getMessage();
            }

            return z;
        }
    }







}
