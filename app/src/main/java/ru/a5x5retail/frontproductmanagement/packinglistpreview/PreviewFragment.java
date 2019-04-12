package ru.a5x5retail.frontproductmanagement.packinglistpreview;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;

import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.checkinglistinc.CheckingListIncActivity;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;
import ru.a5x5retail.frontproductmanagement.сheckinglist.document.CheckingListGoodsActivity;

public class PreviewFragment extends Fragment {
        public PreviewFragment() {
        // Required empty public constructor
    }


    public static PreviewFragment newInstance() {
        PreviewFragment fragment = new PreviewFragment();
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
        View view = inflater.inflate(R.layout.fragment_preview, container, false);
        initUi(view);
        initViewModel();
        return view;

    }

    TextView text_view_1;
    FloatingActionButton fab;
    private void initUi(View view) {
        text_view_1 = view.findViewById(R.id.text_view_1);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createIntent();
            }
        });
    }


    PackingListPreviewViewModel viewModel;
    private void initViewModel(){
        viewModel = ViewModelProviders.of(getActivity()).get(PackingListPreviewViewModel.class);
        updateUi();
    }

    private void updateUi() {
        text_view_1.setText(viewModel.head.NameDoc);
    }

    private void createIntent(){
        if (Constants.getCurrentDoc().getTypeOfDocument() == Constants.TypeOfDocument.OUTER_INCOME ||
                Constants.getCurrentDoc().getTypeOfDocument() == Constants.TypeOfDocument.INNER_INCOME) {
            Intent intent1 = new Intent(getActivity(), CheckingListIncActivity.class);
            intent1.putExtra(Constants.PACKINGLISTHEAD_CONST, viewModel.head);
            startActivity(intent1);
        } else {
            Intent intent1 = new Intent(getActivity(), CheckingListGoodsActivity.class);
            intent1.putExtra("gggg", UUID.fromString(viewModel.head.Guid));
            startActivity(intent1);
        }
    }

}
