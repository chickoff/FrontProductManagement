package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.db.models.ContractorExtendedInfo;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.ExtendedContractorInfoViewModel;
import ru.a5x5retail.frontproductmanagement.base.TestFragment;


public class ContractorInfoSubFragment extends TestFragment<ExtendedContractorInfoViewModel> {

    public ContractorInfoSubFragment() {
        // Required empty public constructor
    }

    public static ContractorInfoSubFragment newInstance(String param1, String param2) {
        ContractorInfoSubFragment fragment = new ContractorInfoSubFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contractor_info_sub, container, false);
        initUi(view);
        initViewModel();
        return view;
    }


    private TextView tv_1;
    private ImageView edi_group_iv_1,edi_tp_group_iv_1,rpbpp_group_iv_1,cz_group_iv_1, dp_group_iv_1;

    private void initUi(View view) {
        tv_1 = view.findViewById(R.id.tv_1);
        edi_group_iv_1 = view.findViewById(R.id.edi_group_iv_1);
        edi_tp_group_iv_1 = view.findViewById(R.id.edi_tp_group_iv_1);
        rpbpp_group_iv_1 = view.findViewById(R.id.rpbpp_group_iv_1);
        cz_group_iv_1 = view.findViewById(R.id.cz_group_iv_1);
        dp_group_iv_1 = view.findViewById(R.id.dp_group_iv_1);
    }

    private void initViewModel() {
        FragmentActivity activity = getActivity();
        setViewModel(ViewModelProviders.of(activity).get(ExtendedContractorInfoViewModel.class));
    }

    private void updateUi() {
        ContractorExtendedInfo info = getViewModel().getContractorExtendedInfo();

        if (info == null) return;
        tv_1.setText(info.contractorName);
        setImage(info.edi,edi_group_iv_1);
        setImage(info.ediTp,edi_tp_group_iv_1);
        setImage(info.rpbpp,rpbpp_group_iv_1);
        setImage(info.cz,cz_group_iv_1);
        setImage(info.dp,dp_group_iv_1);
    }


    private void setImage(int i, ImageView iv) {
        if (i == 1 ) {
            iv.setImageResource(R.drawable.ic_check_circle_black_24dp);
        } else {
            iv.setImageResource(R.drawable.ic_highlight_off_red_24dp);
        }
    }

    @Override
    protected void viewModelDataIsChanged() {
        updateUi();
    }

    @Override
    public void listenerChangedListenerRemove() {
        //NULL
    }

    @Override
    public void listenerChangedListenerAdded() {

        updateUi();
    }
}
