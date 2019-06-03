package ru.a5x5retail.frontproductmanagement.assortmentcard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.BaseFragment;
import ru.a5x5retail.frontproductmanagement.db.models.AssortmentSku;
import ru.a5x5retail.frontproductmanagement.interfaces.IReceiveScanerMessageListener;


public class SkuInfoFragment extends BaseFragment
    implements ISkuInfoView
{
    public static SkuInfoFragment newInstance() {
        SkuInfoFragment fragment = new SkuInfoFragment();
        return fragment;
    }

    public SkuInfoFragment() {
        // Required empty public constructor
    }

    @InjectPresenter
    SkuInfoPresenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sku_info, container, false);
        initView(view);
        return view;
    }

   private TextView text_view_1,text_view_4,text_view_6,text_view_8,text_view_10,text_view_12;

    private void initView(View view){

        text_view_1 = view.findViewById(R.id.text_view_1);
        text_view_4 = view.findViewById(R.id.text_view_4);
        text_view_6 = view.findViewById(R.id.text_view_6);
        text_view_8 = view.findViewById(R.id.text_view_8);
        text_view_10 = view.findViewById(R.id.text_view_10);
        text_view_12 = view.findViewById(R.id.text_view_12);

    }
/*
    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity instanceof AssortmentCardActivity) {
            ((AssortmentCardActivity) activity).
                    setReceiveScanerMessageListener(
                            new IReceiveScanerMessageListener() {
                                @Override
                                public void receiveMessage(String message) {
                                    receiveBarcode(message);
                                }
                            });
        }
    }

    private void receiveBarcode(String message) {


    }*/

    @Override
    public void updateUi() {
        text_view_1.setText(presenter.sku.nameLong);
        text_view_4.setText(String.valueOf(presenter.sku.code));
        text_view_6.setText(presenter.sku.measureUnitIdd);
        text_view_8.setText(presenter.sku.qtyNow.toString());
        text_view_10.setText(presenter.sku.priceRegular.toString());
        text_view_12.setText(presenter.sku.priceCash.toString());

    }

    @Override
    public void clearUi() {
        text_view_1.setText("");
        text_view_4.setText("");
        text_view_6.setText("");
        text_view_8.setText("");
        text_view_10.setText("");
        text_view_12.setText("");
    }
}
