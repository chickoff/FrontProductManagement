package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.a5x5retail.frontproductmanagement.ItemsRecyclerViewDecoration;
import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.adapters.abstractadapters.IRecyclerViewItemShortClickListener;
import ru.a5x5retail.frontproductmanagement.adapters.viewadapters.BasicRecyclerViewAdapter;
import ru.a5x5retail.frontproductmanagement.adapters.viewholders.BasicViewHolder;
import ru.a5x5retail.frontproductmanagement.adapters.BasicViewHolderFactory;
import ru.a5x5retail.frontproductmanagement.base.BaseFragment;

import ru.a5x5retail.frontproductmanagement.db.models.ContractorExtendedInfo;
import ru.a5x5retail.frontproductmanagement.db.models.PlanIncome;

import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.dlgfragments.PlanIncomeSwitchDialogFragment;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments.presenter.PlanIncomeInfoSubPresenter;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments.view.IPlanIncomeInfoSubView;

import static ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.creators.newinvoice.CreateNewInvoiceActivity.BASIS_OF_CREATION_ON_PP;

public class PlanIncomeInfoSubFragment extends BaseFragment implements IPlanIncomeInfoSubView {


    @InjectPresenter
    PlanIncomeInfoSubPresenter presenter;

    public PlanIncomeInfoSubFragment() {
        // Required empty public constructor
    }


    public static PlanIncomeInfoSubFragment newInstance() {
        PlanIncomeInfoSubFragment fragment = new PlanIncomeInfoSubFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_plan_income_info_sub, container, false);
        initUi(view);
       return view;
    }


    private RecyclerView recyclerView;
    private BasicRecyclerViewAdapter<PlanIncome> adapter ;



    private void initUi(View view) {
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
                .setHolderFactory(new InvoiceRecyclerViewHolderFactory());



        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemsRecyclerViewDecoration());
    }

   /* private void init(View view) {

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
                .setSourceList(getViewModel().getPlanIncomeList())
                ;

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemsRecyclerViewDecoration());


    }*/

    private void planIncomeRecyclerViewShortClick(int pos, View view, final PlanIncome innerItem) {

        final PlanIncomeSwitchDialogFragment dlg = new PlanIncomeSwitchDialogFragment();
        dlg.setResultListener(new PlanIncomeSwitchDialogFragment.PlanIncomeSwithDialogFragmentResult() {
            @Override
            public void createInvoice() {
                createInvoiceClick(innerItem);
            }

            @Override
            public void cancelDlg() {

            }
        });

        dlg.setTitle(innerItem.numDoc);
        dlg.show(getFragmentManager(),"fdf");
    }

    private void createInvoiceClick(PlanIncome innerItem) {
        ContractorExtendedInfo ci = presenter.getContractorExtendedInfo();
        if ((ci.edi == 0) || (ci.rpbpp == 1 || ci.ediTp == 1)) {
            ProdManApp.Activities.createNewInvoiceActivity(getActivity(),BASIS_OF_CREATION_ON_PP,null,
                    innerItem,201);
        } else {
            showEventToast("Нельзя создать накладную из ПП. Смотрите информацию по поставщику",0);
        }
    }



    public void updateUi() {
        if (presenter.getPlanIncomeList() == null) return;

        adapter.setSourceList(presenter.getPlanIncomeList());
        adapter.notifyDataSetChanged();
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
