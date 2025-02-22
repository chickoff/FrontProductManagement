package ru.a5x5retail.frontproductmanagement.filters.filterfragments.contractorfilter;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.base.BaseFragment;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorInfo;

public class ContractorFilterFragment extends BaseFragment
implements IContractorFilterView
{



    public ContractorFilterFragment() {
        // Required empty public constructor
    }


    public static ContractorFilterFragment newInstance() {
        ContractorFilterFragment fragment = new ContractorFilterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contractor_filter, container, false);
        //initViewModel();
        init(view);
        return view;
    }

    RecyclerView recyclerView;
    FilteredRecyclerViewAdapter adapter;
    EditText searchView;
    private IFilterFragmentCompleteListener mListener;

    @InjectPresenter
    ContractorFilterPresenter presenter;

    private void init(View view) {

        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.f_contctr_filter_recyclerview);
        adapter = new FilteredRecyclerViewAdapter();
        adapter.setHolderFactory(new ContractorInfoViewHolderFactory());
        adapter.setLayout(R.layout.item_default_1);
        adapter.setShortClickListener(new IRecyclerViewItemShortClickListener<ContractorInfo>() {
            @Override
            public void OnShortClick(int pos, View view, ContractorInfo innerItem) {
                shortClick(pos,innerItem);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemsRecyclerViewDecoration());
    }

    private void shortClick(int pos, ContractorInfo item) {
        if (mListener != null) {
            mListener.setResult(item);
        }
    }

    private void addSearchViewEvent() {

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        addSearchViewEvent();
    }

    /*private void initViewModel() {
        viewModel = new ContractorFilterViewModel();
        try {
            viewModel.Load();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFilterFragmentCompleteListener) {
            mListener = (IFilterFragmentCompleteListener) context;
        }
    }

    @Override
    public void updateUi() {
        adapter.setRawSourceList(presenter.getContractorList());
        addSearchViewEvent();
        if (searchView.getText().length() > 0 ) {
            adapter.getFilter().filter(searchView.getText());
        }
    }

    public class FilteredRecyclerViewAdapter extends BasicRecyclerViewAdapter<ContractorInfo>
            implements Filterable {
        private List<ContractorInfo> rawList;
        private List<ContractorInfo> contactListFilteredList;

        public BasicRecyclerViewAdapter<ContractorInfo> setRawSourceList(List<ContractorInfo> source) {
            rawList = source;
            setSourceList(source);
            notifyDataSetChanged();
            return this;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    String charString = constraint.toString();

                    if (charString.equals("")) {
                        contactListFilteredList = rawList;
                        if (rawList == null) {
                            int a = 1;
                            a=a;
                        }
                    } else {
                        List<ContractorInfo> filteredList = new ArrayList<>();
                        for (ContractorInfo row : rawList) {

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
                    contactListFilteredList = (ArrayList<ContractorInfo>) results.values;
                    //if (contactListFilteredList == null) return;
                    FilteredRecyclerViewAdapter.this.setSourceList(contactListFilteredList);
                    notifyDataSetChanged();
                }
            };
        }
    }

    public class ContractorInfoViewHolder extends BasicViewHolder<ContractorInfo> {

        TextView i_def_1_textbox_1;

        public ContractorInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            i_def_1_textbox_1 = itemView.findViewById(R.id.i_def_1_textbox_1);

        }

        @Override
        public void setSource(ContractorInfo source) {
            i_def_1_textbox_1.setText(source.nameLong);
        }
    }

    public class ContractorInfoViewHolderFactory extends BasicViewHolderFactory {
        @Override
        public BasicViewHolder getNewInstance(View itemView) {
            return new ContractorInfoViewHolder(itemView);
        }
    }

    public class ItemsRecyclerViewDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.top = 2;
        }
    }
}
