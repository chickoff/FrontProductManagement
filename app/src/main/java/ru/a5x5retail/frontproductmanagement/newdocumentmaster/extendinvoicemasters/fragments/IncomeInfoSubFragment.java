package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments;


import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.ItemsRecyclerViewDecoration;
import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;

import ru.a5x5retail.frontproductmanagement.db.models.ContractorExtendedInfo;
import ru.a5x5retail.frontproductmanagement.db.models.InvoiceHead;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.ExtendedContractorInfoViewModel;
import ru.a5x5retail.frontproductmanagement.base.TestFragment;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.dlgfragments.InvoiceSwitchDialogFragment;

import static ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.creators.newinvoice.CreateNewInvoiceActivity.BASIS_OF_CREATION_NEW;


public class IncomeInfoSubFragment extends TestFragment<ExtendedContractorInfoViewModel> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public IncomeInfoSubFragment() {
        // Required empty public constructor
    }


    public static IncomeInfoSubFragment newInstance(String param1, String param2) {
        IncomeInfoSubFragment fragment = new IncomeInfoSubFragment();
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

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income_info_sub, container, false);
        init(view);
        return view;
    }


    private RecyclerView recyclerView;
    private BasicRecyclerViewAdapter<InvoiceHead> adapter ;
    private FloatingActionButton fab;

    private void init(View view) {

        initViewModel();
        recyclerView = view.findViewById(R.id.recyclerView);

        adapter = new BasicRecyclerViewAdapter<>();
        adapter
                .setShortClickListener(new IRecyclerViewItemShortClickListener<InvoiceHead>() {
                    @Override
                    public void OnShortClick(int pos,View view, InvoiceHead innerItem) {
                        invoiceRecyclerViewShortClick(pos,view, innerItem);
                    }
                })
                .setLayout(R.layout.item_invoice_head_def_2)
                .setHolderFactory(new InvoiceRecyclerViewHolderFactory())
                .setSourceList(getViewModel().getInvoiceHeadList());

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemsRecyclerViewDecoration());

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabOnClick();
            }
        });
    }

    private void fabOnClick() {

        ProdManApp.Activities.createNewInvoiceActivity(getActivity(),BASIS_OF_CREATION_NEW,
                getViewModel().getContractorExtendedInfo(),null,201);


    }

    public int DpToPx( int dp){

        Resources r  = getContext().getResources();
        int px    = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return px;

    }

    private void invoiceRecyclerViewShortClick(int pos, View view, final InvoiceHead innerItem) {
        final InvoiceSwitchDialogFragment dlg = new InvoiceSwitchDialogFragment();
        dlg.setResultListener(new InvoiceSwitchDialogFragment.InvoiceSwithDialogFragmentResult() {
            @Override
            public void createCheckList() {
                IncomeInfoSubFragment.this.createCheckList(innerItem);
            }

            @Override
            public void cancelDlg() {

            }
        });

        dlg.setTitle(innerItem.numDoc);
        dlg.show(getFragmentManager(),"fdf");
    }

    private void createCheckList(InvoiceHead innerItem) {
        try {
            getViewModel().CreateNewCheckList(innerItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initViewModel() {
        FragmentActivity activity = getActivity();
        setViewModel( ViewModelProviders.of(activity).get(ExtendedContractorInfoViewModel.class));

    }

    @Override
    protected void viewModelDataIsChanged() {
        updateUi();
    }

    @SuppressLint("RestrictedApi")
    private void updateUi() {
        ContractorExtendedInfo ci = getViewModel().getContractorExtendedInfo();
        if (ci == null) return;
        if (getViewModel().getInvoiceHeadList() == null) return;

        if ((ci.ediTp == 0) || (ci.ediTp == 1 || ci.rpbpp == 1)) {
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.VISIBLE);   // тут нужен инвизибл !!!
        }

        adapter.setSourceList(getViewModel().getInvoiceHeadList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void listenerChangedListenerRemove() {

    }

    @Override
    public void listenerChangedListenerAdded() {

        updateUi();
    }

    public class InvoiceRecyclerViewHolder extends BasicViewHolder<InvoiceHead> {

        public InvoiceRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            group_1_tv_1 = itemView.findViewById(R.id.group_1_tv_1);
            group_2_tv_1 = itemView.findViewById(R.id.group_2_tv_1);
            group_3_tv_1 = itemView.findViewById(R.id.group_3_tv_1);
            group_3_tv_2 = itemView.findViewById(R.id.group_3_tv_2);
        }

        private TextView
                group_1_tv_1,
                group_2_tv_1,
                group_3_tv_1,
                group_3_tv_2;

        @Override
        public void setSource(InvoiceHead source) {
            group_1_tv_1.setText(source.numDoc);
            group_2_tv_1.setText(source.dateDoc.toString());
            group_3_tv_1.setText(source.summ);
            group_3_tv_2.setText(source.summVat);
        }
    }

    public class InvoiceRecyclerViewHolderFactory extends BasicViewHolderFactory {

        @Override
        public BasicViewHolder getNewInstance(View itemView) {
            return new InvoiceRecyclerViewHolder(itemView);
        }
    }
}
