package ru.a5x5retail.frontproductmanagement.filters.skufilter;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.ItemsRecyclerViewDecoration;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.base.BaseAppCompatActivity;
import ru.a5x5retail.frontproductmanagement.base.BaseViewModel;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;

import static ru.a5x5retail.frontproductmanagement.configuration.Constants.FILTER_DATA_REQUEST_CONST;

public class SkuFilterActivity extends BaseAppCompatActivity
implements ISkuFilterView
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sku_filter);
        init();
        initUi();
        //initViewModel();
    }

    @SuppressLint("RestrictedApi")
    private void init() {
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent() != null) {
            String checkingListGuid = getIntent().getStringExtra(FILTER_DATA_REQUEST_CONST);
            presenter.setCheckingListGuid(checkingListGuid);
        }


    }


    private SearchView searchView;
    private RecyclerView recyclerView;
    private SkuRecyclerViewAdapter adapter;
   // private SkuFilterViewModel viewModel;
   @InjectPresenter
    SkuFilterPresenter presenter;
    private void initUi() {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new SkuRecyclerViewAdapter();
        adapter.setHolderFactory(new SkuRecyclerViewViewHolderFactory());
        adapter.setLayout(R.layout.item_code_namelong_1);
        adapter.setShortClickListener(new IRecyclerViewItemShortClickListener<CodeInfo>() {
            @Override
            public void OnShortClick(int pos, View view, CodeInfo innerItem) {
                setResult(innerItem);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemsRecyclerViewDecoration());
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return true;
            }
        });
    }

    /*private void initViewModel() {

        String checkingListGuid = getIntent().getStringExtra(FILTER_DATA_REQUEST_CONST);

        viewModel = ViewModelProviders.of(this).get(SkuFilterViewModel.class);
        viewModel.addDataChangedListener(new BaseViewModel.IDataChangedListener() {
            @Override
            public void dataIsChanged() {
                updateUi();
            }
        });
        viewModel.setCaseOfSource(SkuFilterViewModel.ONE_SOURCE);
        viewModel.setCheckingListGuid(checkingListGuid);
        try {
            viewModel.Load();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/



    @Override
    protected void Test() {
        super.Test();
    }

    @Override
    public void updateUi() {
        adapter.setRawSourceList(presenter.getCodeInfoList());
        adapter.notifyDataSetChanged();
    }

    private void setResult(CodeInfo codeInfo) {
        Intent result = new Intent();
        result.putExtra(Constants.FILTER_DATA_RESPONSE_CONST,codeInfo);
        setResult(RESULT_OK,result);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home :
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    public class SkuRecyclerViewAdapter extends BasicRecyclerViewAdapter<CodeInfo>
            implements Filterable {

        private List<CodeInfo> rawList;
        private List<CodeInfo> contactListFilteredList;


        public BasicRecyclerViewAdapter<CodeInfo> setRawSourceList(List<CodeInfo> source) {
            rawList = source;
            setSourceList(source);
            return this;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    String charString = constraint.toString();
                    if (charString.isEmpty()) {
                        contactListFilteredList = rawList;
                    } else {
                        List<CodeInfo> filteredList = new ArrayList<>();
                        for (CodeInfo row : rawList) {

                            if (row.nameLong.toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }
                        contactListFilteredList = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = contactListFilteredList;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    contactListFilteredList = (ArrayList<CodeInfo>) results.values;
                    SkuRecyclerViewAdapter.this.setSourceList(contactListFilteredList);
                    notifyDataSetChanged();
                }
            };
        }
    }

    public class SkuRecyclerViewViewHolder extends BasicViewHolder<CodeInfo> {

        TextView text_box_1,text_box_2;

        public SkuRecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            text_box_1 = itemView.findViewById(R.id.text_box_1);
            text_box_2 = itemView.findViewById(R.id.text_box_2);
        }

        @Override
        public void setSource(CodeInfo source) {
            text_box_1.setText(String.valueOf(source.code));
            text_box_2.setText(source.nameLong);
        }
    }

    public class SkuRecyclerViewViewHolderFactory extends BasicViewHolderFactory {
        @Override
        public BasicViewHolder getNewInstance(View itemView) {
            return new SkuRecyclerViewViewHolder(itemView);
        }
    }
}
