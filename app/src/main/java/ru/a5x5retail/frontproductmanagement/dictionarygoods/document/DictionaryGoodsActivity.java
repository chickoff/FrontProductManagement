package ru.a5x5retail.frontproductmanagement.dictionarygoods.document;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.configuration.AppConfigurator;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.db.models.DictionaryListGoods;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.document.viewmodel.DictionaryListGoodsViewModel;
import ru.a5x5retail.frontproductmanagement.dictionarygoods.interfaces.IRecyclerViewItemClick;

public class DictionaryGoodsActivity extends AppCompatActivity
        implements IRecyclerViewItemClick<DictionaryListGoods>
{

    //DictionaryGoodsRvAdapter
    private String headerGUID,imeiID;
    //обьявляем viewModel
    private DictionaryListGoodsViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_goods);

        this.headerGUID = getIntent().getSerializableExtra("checkingListHeadGUID").toString();



        imeiID= AppConfigurator.getDeviceId(getApplicationContext());

        initViewModel();
        initRecyclerView();


        EditText et = (EditText)findViewById(R.id.searchEditText);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.Load(imeiID,s.toString(),headerGUID);
                initRecyclerView();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    //создаем вью модель в провайдере
    private void initViewModel(){
        viewModel = ViewModelProviders.of(this).get(DictionaryListGoodsViewModel.class);
        loadViewModel();
    }

    private void loadViewModel(){
        if (viewModel.getState() == Constants.ViewModelStateEnum.LOADED)
            return;

        viewModel.Load(imeiID,null,headerGUID);


    }

    private void initRecyclerView(){
        RecyclerView docItemsRecyclerView = findViewById(R.id.recyclerViewDict);
        DictionaryGoodsRvAdapter ra =new DictionaryGoodsRvAdapter(viewModel.getItemList(),this,headerGUID);
        docItemsRecyclerView.setAdapter(ra);




    }


    @Override
    public void OnClick(int pos, DictionaryListGoods innerItem) {

    }
}
