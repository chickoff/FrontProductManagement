package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.BaseFragment;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments.presenter.ExtendedContractorInfoPresenter;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments.view.IExtendedContractorInfoView;

public class ExtendedContractorInfoFragment extends BaseFragment
implements IExtendedContractorInfoView
{


    public ExtendedContractorInfoFragment() {
    }

    @InjectPresenter
    ExtendedContractorInfoPresenter presenter;

    public static ExtendedContractorInfoFragment newInstance() {
        ExtendedContractorInfoFragment fragment = new ExtendedContractorInfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_extended_contractor_info, container, false);
        initUi(view);
        return view;
    }

    FrameLayout fragment_frame_layout;
    BottomNavigationView bottomNavigationView;

    private void initUi(View view) {
        fragment_frame_layout = view.findViewById(R.id.fragment_frame_layout);
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.invoice_nav_item :
                        presenter.showIncomeInfoSub();
                        return true;

                    case R.id.plan_income_item :
                        presenter.showPlanIncomeInfoSub();
                        return true;

                    case R.id.contractor_info_item :
                        presenter.showContractorInfoSub();
                        return true;

                    default :return true;
                }
            }
        });
    }



    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fmTra = fragmentManager.beginTransaction();
        fmTra.replace(R.id.fragment_frame_layout, fragment);
        fmTra.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void updateUi() {

    }

    @Override
    public void showIncomeInfoSub() {
        replaceFragment(IncomeInfoSubFragment.newInstance());
    }

    @Override
    public void showPlanIncomeInfoSub() {
        replaceFragment(PlanIncomeInfoSubFragment.newInstance());
    }

    @Override
    public void showContractorInfoSub() {
        replaceFragment(ContractorInfoSubFragment.newInstance());
    }
}
