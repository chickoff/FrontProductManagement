package ru.a5x5retail.frontproductmanagement.inventories.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.a5x5retail.frontproductmanagement.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditInventoryStatementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditInventoryStatementFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public EditInventoryStatementFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EditInventoryStatementFragment newInstance() {
        EditInventoryStatementFragment fragment = new EditInventoryStatementFragment();
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
        return inflater.inflate(R.layout.fragment_edit_inventory_statement, container, false);
    }

}
