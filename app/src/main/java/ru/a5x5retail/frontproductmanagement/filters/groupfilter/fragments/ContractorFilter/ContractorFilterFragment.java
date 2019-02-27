package ru.a5x5retail.frontproductmanagement.filters.groupfilter.fragments.ContractorFilter;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.db.models.CodeInfo;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorInfo;
import ru.a5x5retail.frontproductmanagement.filters.groupfilter.IFilterCompleteListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContractorFilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContractorFilterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ContractorFilterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContractorFilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContractorFilterFragment newInstance(String param1, String param2) {
        ContractorFilterFragment fragment = new ContractorFilterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contractor_filter, container, false);
        initViewModel();
        init(view);
        return view;
    }

    RecyclerView recyclerView;
    FilteredRecyclerViewAdapter adapter;
    SearchView searchView;
    ContractorFilterViewModel viewModel;
    private IFilterCompleteListener mListener;

    private void init(View view) {

        searchView = view.findViewById(R.id.searchView);
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

        recyclerView = view.findViewById(R.id.f_contctr_filter_recyclerview);
        adapter = new FilteredRecyclerViewAdapter();
        adapter.setRawSourceList(viewModel.getContractorList());
        adapter.setHolderFactory(new ContractorInfoViewHolderFactory());
        adapter.setLayout(R.layout.item_default_1);
        adapter.setShortClickListener(new BasicRecyclerViewAdapter.IRecyclerViewItemShortClickListener<ContractorInfo>() {
            @Override
            public void OnShortClick(int pos, ContractorInfo innerItem) {
                shortClick(pos,innerItem);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void shortClick(int pos, ContractorInfo item) {

        List<CodeInfo> skuList = null;
        try {
            skuList = viewModel.getCode(item.guid);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (mListener != null) {
            mListener.setResult(skuList);
        }
    }


    private void initViewModel() {
        viewModel = new ContractorFilterViewModel();
        try {
            viewModel.Load();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFilterCompleteListener) {
            mListener = (IFilterCompleteListener) context;
        }
    }

    public class FilteredRecyclerViewAdapter extends BasicRecyclerViewAdapter<ContractorInfo>
            implements Filterable {


        private List<ContractorInfo> rawList;
        private List<ContractorInfo> contactListFilteredList;


        public BasicRecyclerViewAdapter<ContractorInfo> setRawSourceList(List<ContractorInfo> source) {
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
}
