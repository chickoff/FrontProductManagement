package ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.BaseFragment;
import ru.a5x5retail.frontproductmanagement.newdocumentmaster.extendinvoicemasters.ExtendedContractorInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExtendedContractorInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExtendedContractorInfoFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String EXTERNAL_CONTRACTOR_GUID = "EXTERNAL_CONTRACTOR_GUID";


    public ExtendedContractorInfoFragment() {
        // Required empty public constructor
    }

    public static ExtendedContractorInfoFragment newInstance() {
        ExtendedContractorInfoFragment fragment = new ExtendedContractorInfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
                        replaceFragment(IncomeInfoSubFragment.newInstance("",""));
                        return true;

                    case R.id.plan_income_item :
                        replaceFragment(PlanIncomeInfoSubFragment.newInstance("",""));
                        return true;

                    case R.id.contractor_info_item :
                        replaceFragment(ContractorInfoSubFragment.newInstance("",""));
                        return true;

                    default :return true;
                }
            }
        });

        if (isFirstStart()) {
            bottomNavigationView.setSelectedItemId(R.id.invoice_nav_item);
        }
    }

    private ExtendedContractorInfoViewModel viewModel;

    private void initViewModel() {
        FragmentActivity activity = getActivity();
        if (activity != null){
        viewModel = ViewModelProviders.of(activity).get(ExtendedContractorInfoViewModel.class);
        }
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
}
