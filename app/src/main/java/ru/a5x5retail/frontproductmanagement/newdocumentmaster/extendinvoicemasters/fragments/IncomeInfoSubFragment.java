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

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.ItemsRecyclerViewDecoration;
import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;

import ru.a5x5retail.frontproductmanagement.base.BaseFragment;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorExtendedInfo;
import ru.a5x5retail.frontproductmanagement.db.models.IncomeInvoiceHead;

import ru.a5x5retail.frontproductmanagement.base.TestFragment;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.dlgfragments.InvoiceSwitchDialogFragment;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments.presenter.IncomeInfoSubPresenter;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments.view.IIncomeInfoSubView;

import static ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.creators.newinvoice.CreateNewInvoiceActivity.BASIS_OF_CREATION_NEW;


public class IncomeInfoSubFragment extends BaseFragment implements IIncomeInfoSubView {


    @InjectPresenter
    IncomeInfoSubPresenter presenter;

    public IncomeInfoSubFragment() {
        // Required empty public constructor
    }


    public static IncomeInfoSubFragment newInstance() {
        IncomeInfoSubFragment fragment = new IncomeInfoSubFragment();
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
    private BasicRecyclerViewAdapter<IncomeInvoiceHead> adapter ;
    private FloatingActionButton fab;

    private void init(View view) {

        initViewModel();
        recyclerView = view.findViewById(R.id.recyclerView);

        adapter = new BasicRecyclerViewAdapter<>();
        adapter
                .setShortClickListener(new IRecyclerViewItemShortClickListener<IncomeInvoiceHead>() {
                    @Override
                    public void OnShortClick(int pos,View view, IncomeInvoiceHead innerItem) {
                        invoiceRecyclerViewShortClick(pos,view, innerItem);
                    }
                })
                .setLayout(R.layout.item_invoice_head_def_2)
                .setHolderFactory(new InvoiceRecyclerViewHolderFactory());
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
                presenter.getContractorExtendedInfo(),null,201);


    }

    public int DpToPx( int dp){

        Resources r  = getContext().getResources();
        int px    = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return px;

    }

    private void invoiceRecyclerViewShortClick(int pos, View view, final IncomeInvoiceHead innerItem) {
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

    private void createCheckList(IncomeInvoiceHead innerItem) {
        presenter.createNewCheckList(innerItem);
        getActivity().finish();
    }

    private void initViewModel() {
        FragmentActivity activity = getActivity();
        //setViewModel( ViewModelProviders.of(activity).get(ExtendedContractorInfoViewModel.class));

    }



    @SuppressLint("RestrictedApi")
    public void updateUi() {
        ContractorExtendedInfo ci = presenter.getContractorExtendedInfo();

        if (ci == null) return;
        if (presenter.getInvoiceHeadList() == null) return;
        if (ci.edi == 0 || ci.ediTp == 1 || ci.rpbpp == 1) {
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.INVISIBLE);   // тут нужен инвизибл !!!
        }
        adapter.setSourceList(presenter.getInvoiceHeadList());
        adapter.notifyDataSetChanged();
    }



    public class InvoiceRecyclerViewHolder extends BasicViewHolder<IncomeInvoiceHead> {

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
        public void setSource(IncomeInvoiceHead source) {
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
