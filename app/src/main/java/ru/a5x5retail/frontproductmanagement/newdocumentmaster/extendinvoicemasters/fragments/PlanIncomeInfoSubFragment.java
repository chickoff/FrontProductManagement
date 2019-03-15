package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.a5x5retail.frontproductmanagement.ItemsRecyclerViewDecoration;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.db.models.PlanIncome;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.ExtendedContractorInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanIncomeInfoSubFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanIncomeInfoSubFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public PlanIncomeInfoSubFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlanIncomeInfoSubFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlanIncomeInfoSubFragment newInstance(String param1, String param2) {
        PlanIncomeInfoSubFragment fragment = new PlanIncomeInfoSubFragment();
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
       View view = inflater.inflate(R.layout.fragment_plan_income_info_sub, container, false);
       init(view);
       return view;
    }

    private ExtendedContractorInfoViewModel viewModel;
    private RecyclerView recyclerView;
    private BasicRecyclerViewAdapter<PlanIncome> adapter ;



    private void init(View view) {

        initViewModel();

        recyclerView = view.findViewById(R.id.recyclerView);

       adapter = new BasicRecyclerViewAdapter<>();
        adapter
                .setShortClickListener(new IRecyclerViewItemShortClickListener<PlanIncome>() {
                    @Override
                    public void OnShortClick(int pos,View view, PlanIncome innerItem) {
                        planIncomeRecyclerViewShortClick(pos,view, innerItem);
                    }
                })
                .setLayout(R.layout.item_plan_income_head_def_1)
                .setHolderFactory(new InvoiceRecyclerViewHolderFactory())
                .setSourceList(viewModel.getPlanIncomeList())
                ;

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemsRecyclerViewDecoration());
    }

    private void planIncomeRecyclerViewShortClick(int pos,View view, PlanIncome innerItem) {
    }

    private void initViewModel() {
        FragmentActivity activity = getActivity();
        viewModel =  ViewModelProviders.of(activity).get(ExtendedContractorInfoViewModel.class);
    }


    public class MyBasicRecyclerViewAdapter<T> extends BasicRecyclerViewAdapter<T> {

    }


    public class InvoiceRecyclerViewHolder extends BasicViewHolder<PlanIncome> {

        public InvoiceRecyclerViewHolder( View itemView) {
            super(itemView);
                    group_1_tv_1 = itemView.findViewById(R.id.group_1_tv_1);
                    group_2_tv_1 = itemView.findViewById(R.id.group_2_tv_1);
                    group_3_tv_1 = itemView.findViewById(R.id.group_3_tv_1);
        }

        private TextView
                    group_1_tv_1
                ,   group_2_tv_1
                ,   group_3_tv_1;

        @Override
        public void setSource(PlanIncome source) {
            group_1_tv_1.setText(source.numDoc);
            group_2_tv_1.setText(source.dateDoc);
            group_3_tv_1.setText(source.qty);
        }
    }

    public class InvoiceRecyclerViewHolderFactory extends BasicViewHolderFactory {

        @Override
        public BasicViewHolder getNewInstance(View itemView) {
            return new InvoiceRecyclerViewHolder(itemView);
        }
    }

}
